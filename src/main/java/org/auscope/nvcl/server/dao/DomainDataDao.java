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
import org.auscope.nvcl.server.vo.DomainDataCollectionVo;
import org.auscope.nvcl.server.vo.DomainDataVo;

/**
 * This Data Access Object (DAO) extends <code>StoredProcedure</code> for
 * calling oracle function. It is used to call oracle function
 * <code>GETDOMAINDATA</code>. This function call take in one parameter :
 * <ul>
 * <li>v_domainlog_id (String)
 * </ul>
 * and return the resultSet as oracle REF Cursor which consists of 4 columns :
 * <ul>
 * <li>SAMPLENUMBER (BigDecimal)
 * <li>STARTVALUE (float)
 * <li>ENDVALUE (float)
 * <li>SAMPLENAME (String)
 * </ul>
 * <p>
 *
 * @author Florence Tan
 */

public class DomainDataDao extends StoredProcedure {

	private static final Logger logger = LogManager.getLogger(DomainDataDao.class);

    private static final String SQL = "GETDOMAINDATA";
    private static final String DOMAINLOGID = "v_domainlog_id";
    private static final String OUTPARAMNAME = "DomainData";


    /**
     * This method define the input parameters and output parameter for calling
     * either the oracle function or stored procedure with name
     * GETDOMAINDATA.
     *
     * @param dataSource
     * @param configVo
     */
    public DomainDataDao(BasicDataSource dataSource) {
        super();
        setDataSource(dataSource);
        setSql(SQL);

        if (dataSource.getDriverClassName().toLowerCase().contains("oracle")) {
            setFunction(true);
            declareParameter(new SqlOutParameter(OUTPARAMNAME,
                    -10, new DomainDataRowMapper()));
					// sqlTypes doesn't have a type for Oracle Ref Cursor.
					// OracleTypes.CURSOR could be used here but it adds
					// the oracle driver as a dependency on the project
        } else {
            declareParameter(new SqlReturnResultSet(OUTPARAMNAME,
                    new DomainDataRowMapper()));
        }
        declareParameter(new SqlParameter(DOMAINLOGID, Types.VARCHAR));
        compile();
    }

    /**
     * Mapped the ResultSet / REF Cursor return from the oracle function by
     * implementing <code>RowMapper</code> interface.
     *
     * @author Florence Tan
     */
    private final class DomainDataRowMapper implements RowMapper<DomainDataVo> {
        /**
         * Processing rows of the input ResultSet on a per-row basis.
         *
         * @param ResultSet
         *            oracle REF Cursor return from the function call
         * @param rownum
         *            required by the mapRow method specify in RowMapper
         *            interface
         * @return Object the result object (DomainDataVo) for the current row
         */
        public DomainDataVo mapRow(ResultSet rs, int rownum) throws SQLException {
            DomainDataVo domainDataVo = new DomainDataVo();
            domainDataVo.setSampleNo(rs.getInt("SAMPLENUMBER"));
            domainDataVo.setStartValue(rs.getFloat("STARTVALUE"));
            domainDataVo.setEndValue(rs.getFloat("ENDVALUE"));
            return domainDataVo;
        }
    }

    /**
     * Execute the function calling with the specified required input parameters
     * and getting the oracle REF Cursor as output.
     *
     * @param domainlogId
     *            domainlog id as string
     */
    @SuppressWarnings("unchecked")
    public DomainDataCollectionVo execute(String logId) {
        ArrayList<DomainDataVo> outs = null;
        try {
            logger.debug("in execute... ");
            Map<String, Object> inputs = new HashMap<String, Object>();
            inputs.put(DOMAINLOGID, logId);
            logger.debug("inputs : " + inputs);
            outs = (ArrayList<DomainDataVo>) super.execute(inputs).get(OUTPARAMNAME);
        } catch (Exception e) {
            logger.error("Exception : " + e);
        }
        return new DomainDataCollectionVo(outs);
    }

}
