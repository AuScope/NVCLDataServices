package org.auscope.nvcl.server.dao;

import java.sql.*;
import java.util.*;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.auscope.nvcl.server.vo.BinnedClassDataVo;

/**
 * This Data Access Object (DAO) extends <code>StoredProcedure</code> for
 * calling oracle function. It is used to call oracle function
 * <code>GETDOWNSAMPLEDLOGVALUES
 * </code> for log type value 1. This function call take in four parameters :
 * <ul>
 * <li>v_logid (String)
 * <li>v_startdepth (float)
 * <li>v_enddepth (float)
 * <li>v_interval (float)
 * </ul>
 * and return the resultSet as oracle REF Cursor which consists of 4 columns :
 * <ul>
 * <li>ROUNDEDDEPTH (double)
 * <li>CLASSCOUNT (int)
 * <li>CLASSTEXT (String)
 * <li>COLOUR (int)
 * </ul>
 * <p>
 *
 * @author Florence Tan
 * @author Peter Warren (CSIRO Earth Science and Resource Engineering)
 */

public class DownSampledClassDataDao extends StoredProcedure {

	private static final Logger logger = LogManager.getLogger(DownSampledClassDataDao.class);

    private static final String SQL = "GETDOWNSAMPLEDLOGVALUES";
    private static final String LOGID = "v_logid";
    private static final String STARTDEPTH = "v_startdepth";
    private static final String ENDDEPTH = "v_enddepth";
    private static final String INTERVAL = "v_interval";
    private static final String OUTPARAMNAME = "LogTypeOne";
    private static final String MINTHRESHOLD = "v_threshold";


    /**
     * This method define the input parameters and output parameter for calling
     * either the oracle function or stored procedure with name
     * GETDOWNSAMPLEDLOGVALUES.
     *
     * @param dataSource
     * @throws SQLException 
     */
    public DownSampledClassDataDao(BasicDataSource dataSource) throws SQLException {
        super();
        setDataSource(dataSource);
        setSql(SQL);


        if (dataSource.getDriverClassName().toLowerCase().contains("oracle")) {
            setFunction(true);
            declareParameter(new SqlOutParameter(OUTPARAMNAME,
                    -10, new LogTypeOneRowMapper()));
					// sqlTypes doesn't have a type for Oracle Ref Cursor.
					// OracleTypes.CURSOR could be used here but it adds
					// the oracle driver as a dependency on the project
        } else {
            declareParameter(new SqlReturnResultSet(OUTPARAMNAME,
                    new LogTypeOneRowMapper()));
        }
        Boolean mintThreshParamPresent=false;
        ResultSet rs = dataSource.getConnection().getMetaData().getProcedureColumns(null, null,SQL , null);
        while (rs.next()) {
            if(rs.getString("COLUMN_NAME")!=null && rs.getString("COLUMN_NAME").toLowerCase().contains(MINTHRESHOLD.toLowerCase())) mintThreshParamPresent=true;
        }
        declareParameter(new SqlParameter(LOGID, Types.VARCHAR));
        declareParameter(new SqlParameter(STARTDEPTH, Types.FLOAT));
        declareParameter(new SqlParameter(ENDDEPTH, Types.FLOAT));
        declareParameter(new SqlParameter(INTERVAL, Types.FLOAT));
        if(mintThreshParamPresent) declareParameter(new SqlParameter(MINTHRESHOLD, Types.FLOAT));
        compile();
    }

    /**
     * Mapped the ResultSet / REF Cursor return from the oracle function by
     * implementing <code>RowMapper</code> interface.
     *
     * @author Florence Tan
     */
    private final class LogTypeOneRowMapper implements RowMapper<BinnedClassDataVo> {
        /**
         * Processing rows of the input ResultSet on a per-row basis.
         *
         * @param ResultSet
         *            oracle REF Cursor return from the function call
         * @param rownum
         *            required by the mapRow method specify in RowMapper
         *            interface
         * @return Object the result object (LogTypeOneVo) for the current row
         */
        public BinnedClassDataVo mapRow(ResultSet rs, int rownum) throws SQLException {
            BinnedClassDataVo logTypeOneVo = new BinnedClassDataVo();
            logTypeOneVo.setRoundedDepth(rs.getFloat("ROUNDEDDEPTH"));
            logTypeOneVo.setClassCount(rs.getInt("CLASSCOUNT"));
            logTypeOneVo.setClassText(rs.getString("CLASSTEXT"));
            logTypeOneVo.setColour(rs.getInt("COLOUR"));
            return logTypeOneVo;
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
     * @return ArrayList of LogTypeOneVo records
     */
    @SuppressWarnings("unchecked")
    public ArrayList<BinnedClassDataVo> execute(String logId,
            float startDepth, float endDepth, float samplingInterval, float minthreshold) {
        ArrayList<BinnedClassDataVo> outs = null;

        try {
            logger.debug("in execute... ");
            Map<String, Object> inputs = new HashMap<String, Object>();
            inputs.put(LOGID, logId);
            inputs.put(STARTDEPTH, startDepth);
            inputs.put(ENDDEPTH, endDepth);
            inputs.put(INTERVAL, samplingInterval);
            inputs.put(MINTHRESHOLD, minthreshold);
            logger.info("inputs : " + inputs);
            outs = (ArrayList<BinnedClassDataVo>) super.execute(inputs).get(OUTPARAMNAME);
        } catch (Exception e) {
            logger.error("Exception : " + e);
        }
        return outs;
    }

}
