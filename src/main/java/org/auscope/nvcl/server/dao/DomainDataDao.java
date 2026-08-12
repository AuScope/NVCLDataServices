package org.auscope.nvcl.server.dao;

import java.sql.*;
import java.util.*;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
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

@Repository
public class DomainDataDao {

    private static final Logger logger = LogManager.getLogger(DomainDataDao.class);

    private static final String SQL = "GETDOMAINDATA";

    private final SimpleJdbcCall call;

    public DomainDataDao(DataSource dataSource,@Value("${jdbc.dbType}") String dbType) {

        if ("oracle".equalsIgnoreCase(dbType)) {

            this.call =
                new SimpleJdbcCall(dataSource)
                    .withFunctionName(SQL)
                    .declareParameters(
                        new SqlOutParameter(
                            "DomainData",
                            Types.REF_CURSOR,
                            new DomainDataRowMapper()),
                        new SqlParameter(
                            "v_domainlog_id",
                            Types.VARCHAR));

        } else {

            this.call =
                new SimpleJdbcCall(dataSource)
                    .withProcedureName(SQL)
                    .declareParameters(
                        new SqlReturnResultSet(
                            "DomainData",
                            new DomainDataRowMapper()),
                        new SqlParameter(
                            "v_domainlog_id",
                            Types.VARCHAR));
        }
    }

    private static class DomainDataRowMapper implements RowMapper<DomainDataVo> {

        @Override
        public DomainDataVo mapRow(
                ResultSet rs,
                int rowNum)
                throws SQLException {

            DomainDataVo vo = new DomainDataVo();

            vo.setSampleNo(rs.getInt("SAMPLENUMBER"));
            vo.setStartValue(rs.getFloat("STARTVALUE"));
            vo.setEndValue(rs.getFloat("ENDVALUE"));

            return vo;
        }
    }

    @SuppressWarnings("unchecked")
    public DomainDataCollectionVo execute(String logId) {

        try {

            Map<String, Object> result =
                    call.execute(
                        Collections.singletonMap(
                            "v_domainlog_id",
                            logId));

            List<DomainDataVo> data =
                    (List<DomainDataVo>)
                    result.get("DomainData");

            return new DomainDataCollectionVo(data);

        }
        catch (Exception ex) {

            logger.error("Failed executing GETDOMAINDATA", ex);

            return new DomainDataCollectionVo(Collections.emptyList());
        }
    }

}
