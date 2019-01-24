package org.auscope.nvcl.server.dao;

import java.sql.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.auscope.nvcl.server.vo.AveragedFloatDataVo;

/**
 * This Data Access Object (DAO) extends <code>StoredProcedure</code> for
 * calling oracle function. It is used to call oracle function
 * <code>GETDOWNSAMPLEDLOGVALUES
 * </code> for log type value 2. This function call take in four parameters :
 * <ul>
 * <li>v_logid (String)
 * <li>v_startdepth (float)
 * <li>v_enddepth (float)
 * <li>v_interval (float)
 * </ul>
 * and return the resultSet as oracle REF Cursor which consists of 2 columns :
 * <ul>
 * <li>ROUNDEDDEPTH (float)
 * <li>AVERAGEVALUE (float)
 * </ul>
 * <p>
 *
 * @author Florence Tan
 * @author Peter Warren (CSIRO Earth Science and Resource Engineering)
 */
public class DownSampledFloatDataDao extends StoredProcedure {

	private static final Logger logger = LogManager.getLogger(DownSampledFloatDataDao.class);

    private static final String SQL = "GETDOWNSAMPLEDLOGVALUES";
    private static final String LOGID = "v_logid";
    private static final String STARTDEPTH = "v_startdepth";
    private static final String ENDDEPTH = "v_enddepth";
    private static final String INTERVAL = "v_interval";
    private static final String OUTPARAMNAME = "LogTypeTwo";


    /**
     * This method define the input parameters and output parameter for calling
     * either the oracle function or a stored procedure with name
     * GETDOWNSAMPLEDLOGVALUES.
     *
     * @param dataSource
     */
    @Autowired
    public DownSampledFloatDataDao(BasicDataSource dataSource) {
        super();
        setDataSource(dataSource);

        setSql(SQL);
        if (dataSource.getDriverClassName().toLowerCase().contains("oracle")) {
            setFunction(true);
            declareParameter(new SqlOutParameter(OUTPARAMNAME,
                    -10, new LogTypeTwoRowMapper()));
					// sqlTypes doesn't have a type for Oracle Ref Cursor.
					// OracleTypes.CURSOR could be used here but it adds
					// the oracle driver as a dependency on the project
        } else {
            declareParameter(new SqlReturnResultSet(OUTPARAMNAME,
                    new LogTypeTwoRowMapper()));
        }

        declareParameter(new SqlParameter(LOGID, Types.VARCHAR));
        declareParameter(new SqlParameter(STARTDEPTH, Types.FLOAT));
        declareParameter(new SqlParameter(ENDDEPTH, Types.FLOAT));
        declareParameter(new SqlParameter(INTERVAL, Types.FLOAT));
        compile();
    }

    /**
     * Mapped the ResultSet / REF Cursor return from the oracle function by
     * implementing <code>RowMapper</code> interface.
     *
     * @author Florence Tan
     *
     */
    private final class LogTypeTwoRowMapper implements RowMapper<AveragedFloatDataVo> {
        /**
         * Processing rows of the input ResultSet on a per-row basis.
         *
         * @param ResultSet
         *            oracle REF Cursor return from the function call
         * @param rownum
         *            required by the mapRow method specify in RowMapper
         *            interface
         * @return Object the result object (LogTypeTwoVo) for the current row
         *
         */
        public AveragedFloatDataVo mapRow(ResultSet rs, int rownum)
                throws SQLException {
            AveragedFloatDataVo logTypeTwoVo = new AveragedFloatDataVo();
            logTypeTwoVo.setRoundedDepth(rs.getFloat("ROUNDEDDEPTH"));
            logTypeTwoVo.setAverageValue(rs.getFloat("AVERAGEVALUE"));
            return logTypeTwoVo;
        }
    }

    /**
     * Execute the function calling with the specified required input parameters
     * and getting the oracle REF Cursor as output.
     *
     * @param logId
     *            unique log id as string
     * @param startDepth
     *            start depth of borehole in float
     * @param endDepth
     *            end depth of borehole in float
     * @param interval
     *            interval of borehole in float
     * @return map returning the mapped resultSet as a Map
     */
    @SuppressWarnings("unchecked")
    public ArrayList<AveragedFloatDataVo> execute(String logId,
            float startDepth, float endDepth, float samplingInterval) {
        ArrayList<AveragedFloatDataVo> outs = null;
        try {
            HashMap<String, Object> inputs = new HashMap<String, Object>();
            inputs.put(LOGID, logId);
            inputs.put(STARTDEPTH, startDepth);
            inputs.put(ENDDEPTH, endDepth);
            inputs.put(INTERVAL, samplingInterval);
            logger.info("inputs : " + inputs);
            outs = (ArrayList<AveragedFloatDataVo>) super.execute(inputs).get(OUTPARAMNAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outs;
    }

}
