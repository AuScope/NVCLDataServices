package org.auscope.nvcl.server.dao;

import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

public abstract class AbstractDownSampledDao<T> {

    protected static final String SQL = "GETDOWNSAMPLEDLOGVALUES";

    protected static final String LOGID = "v_logid";
    protected static final String STARTDEPTH = "v_startdepth";
    protected static final String ENDDEPTH = "v_enddepth";
    protected static final String INTERVAL = "v_interval";
    protected static final String MINTHRESHOLD = "v_threshold";

    private final String outParamName;
    private final Logger logger;

    private final SimpleJdbcCall callWithThreshold;
    private final SimpleJdbcCall callWithoutThreshold;

    private volatile Boolean thresholdSupported = null;

    protected AbstractDownSampledDao(
            DataSource dataSource,
            String dbType,
            String outParamName,
            RowMapper<T> mapper,
            Logger logger) {

        this.outParamName = outParamName;
        this.logger = logger;

        if ("oracle".equalsIgnoreCase(dbType)) {

            this.callWithThreshold =
                    new SimpleJdbcCall(dataSource)
                            .withFunctionName(SQL)
                            .withoutProcedureColumnMetaDataAccess()
                            .declareParameters(
                                    new SqlOutParameter(
                                            outParamName,
                                            Types.REF_CURSOR,
                                            mapper),
                                    new SqlParameter(LOGID, Types.VARCHAR),
                                    new SqlParameter(STARTDEPTH, Types.FLOAT),
                                    new SqlParameter(ENDDEPTH, Types.FLOAT),
                                    new SqlParameter(INTERVAL, Types.FLOAT),
                                    new SqlParameter(MINTHRESHOLD, Types.FLOAT));

            this.callWithoutThreshold =
                    new SimpleJdbcCall(dataSource)
                            .withFunctionName(SQL)
                            .withoutProcedureColumnMetaDataAccess()
                            .declareParameters(
                                    new SqlOutParameter(
                                            outParamName,
                                            Types.REF_CURSOR,
                                            mapper),
                                    new SqlParameter(LOGID, Types.VARCHAR),
                                    new SqlParameter(STARTDEPTH, Types.FLOAT),
                                    new SqlParameter(ENDDEPTH, Types.FLOAT),
                                    new SqlParameter(INTERVAL, Types.FLOAT));

        } else {

            this.callWithThreshold =
                    new SimpleJdbcCall(dataSource)
                            .withProcedureName(SQL)
                            .withoutProcedureColumnMetaDataAccess()
                            .declareParameters(
                                    new SqlReturnResultSet(
                                            outParamName,
                                            mapper),
                                    new SqlParameter(LOGID, Types.VARCHAR),
                                    new SqlParameter(STARTDEPTH, Types.FLOAT),
                                    new SqlParameter(ENDDEPTH, Types.FLOAT),
                                    new SqlParameter(INTERVAL, Types.FLOAT),
                                    new SqlParameter(MINTHRESHOLD, Types.FLOAT));

            this.callWithoutThreshold =
                    new SimpleJdbcCall(dataSource)
                            .withProcedureName(SQL)
                            .withoutProcedureColumnMetaDataAccess()
                            .declareParameters(
                                    new SqlReturnResultSet(
                                            outParamName,
                                            mapper),
                                    new SqlParameter(LOGID, Types.VARCHAR),
                                    new SqlParameter(STARTDEPTH, Types.FLOAT),
                                    new SqlParameter(ENDDEPTH, Types.FLOAT),
                                    new SqlParameter(INTERVAL, Types.FLOAT));
        }

        this.callWithThreshold.compile();
        this.callWithoutThreshold.compile();
    }

    private Map<String, Object> buildInputs(
            String logId,
            float startDepth,
            float endDepth,
            float interval) {

        Map<String, Object> inputs = new HashMap<>();

        inputs.put(LOGID, logId);
        inputs.put(STARTDEPTH, startDepth);
        inputs.put(ENDDEPTH, endDepth);
        inputs.put(INTERVAL, interval);

        return inputs;
    }

    private Map<String, Object> buildInputsWithThreshold(
            String logId,
            float startDepth,
            float endDepth,
            float interval,
            float minThreshold) {

        Map<String, Object> inputs =
                buildInputs(
                        logId,
                        startDepth,
                        endDepth,
                        interval);

        inputs.put(MINTHRESHOLD, minThreshold);

        return inputs;
    }

    @SuppressWarnings("unchecked")
    public List<T> execute(
            String logId,
            float startDepth,
            float endDepth,
            float interval,
            float minThreshold) {

        try {

            Map<String, Object> result;

            if (thresholdSupported == Boolean.FALSE) {

                result =
                        callWithoutThreshold.execute(
                                buildInputs(
                                        logId,
                                        startDepth,
                                        endDepth,
                                        interval));

            } else {

                try {

                    result =
                            callWithThreshold.execute(
                                    buildInputsWithThreshold(
                                            logId,
                                            startDepth,
                                            endDepth,
                                            interval,
                                            minThreshold));

                    thresholdSupported = true;

                } catch (Exception ex) {

                    logger.warn(
                            "Stored procedure does not support {}. Falling back.",
                            MINTHRESHOLD);

                    thresholdSupported = false;

                    result =
                            callWithoutThreshold.execute(
                                    buildInputs(
                                            logId,
                                            startDepth,
                                            endDepth,
                                            interval));
                }
            }

            List<T> rows =
                    (List<T>) result.get(outParamName);

            return rows == null
                    ? Collections.emptyList()
                    : rows;

        } catch (Exception ex) {

            logger.error(
                    "Failed executing {}",
                    SQL,
                    ex);

            return Collections.emptyList();
        }
    }
}