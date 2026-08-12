package org.auscope.nvcl.server.dao;

import java.sql.Types;
import java.util.Collections;
import java.util.Map;
import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.auscope.nvcl.server.vo.LogExtentsVo;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;


/**
 * This Data Access Object (DAO) extends <code>StoredProcedure</code> for
 * calling a database stored procedure. It is used to call
 * <code>GETLOGEXTENTS</code>. This function call take in one parameter :
 * <ul>
 * <li>v_logid(String)</li>
 * </ul>
 * and return a resultSet which consists of 2 value :
 * <ul>
 * <li>v_minval (float)</li>
 * <li>v_maxval (float)</li>
 * </ul>
 * <p>
 *
 * @author Peter Warren
 */

@Repository
public class LogExtentsDao {

    private static final Logger logger =
            LogManager.getLogger(LogExtentsDao.class);

    private static final String SQL = "GETLOGEXTENTS";

    private static final String LOGID = "v_logid";
    private static final String MINVALUE = "v_minval";
    private static final String MAXVALUE = "v_maxval";

    private final SimpleJdbcCall call;

    public LogExtentsDao(DataSource dataSource) {

        this.call =
                new SimpleJdbcCall(dataSource)
                        .withProcedureName(SQL)
						.withoutProcedureColumnMetaDataAccess()
                        .declareParameters(
                                new SqlParameter(
                                        LOGID,
                                        Types.VARCHAR),

                                new SqlOutParameter(
                                        MINVALUE,
                                        Types.FLOAT),

                                new SqlOutParameter(
                                        MAXVALUE,
                                        Types.FLOAT));
    }

    public LogExtentsVo execute(String logId) {

        try {

            Map<String, Object> inputs =
                    Collections.singletonMap(
                            LOGID,
                            logId);

            Map<String, Object> result =
                    call.execute(inputs);

            Number min = (Number) result.get(MINVALUE);

			Number max = (Number) result.get(MAXVALUE);

			return new LogExtentsVo(min == null ? 0f : min.floatValue(),max == null ? 0f : max.floatValue());

        } catch (Exception ex) {

            logger.error(
                    "Failed executing {}",
                    SQL,
                    ex);

            return new LogExtentsVo(
                    0f,
                    0f);
        }
    }
}
