package org.auscope.nvcl.server.dao;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.auscope.nvcl.server.vo.LogExtentsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

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

public class LogExtentsDao extends StoredProcedure {

	private static final Logger logger = LogManager
			.getLogger(LogExtentsDao.class);

	private static final String SQL = "GETLOGEXTENTS";
	private static final String LOGID = "v_logid";
	private static final String MINVALUE = "v_minval";
	private static final String MAXVALUE = "v_maxval";

	/**
	 * This method define the input parameters and output parameter for calling
	 * either the oracle function or stored procedure with name GETDOMAINDATA.
	 *
	 * @param dataSource
	 */
	@Autowired
	public LogExtentsDao(BasicDataSource dataSource) {
		super(dataSource, SQL);

		declareParameter(new SqlParameter(LOGID, Types.VARCHAR));
		declareParameter(new SqlOutParameter(MINVALUE, Types.FLOAT));
		declareParameter(new SqlOutParameter(MAXVALUE, Types.FLOAT));

	}

	/**
	 * Execute the function calling with the specified required input parameters
	 * and getting the oracle REF Cursor as output.
	 *
	 * @param domainlogId
	 *            domainlog id as string
	 */
	public LogExtentsVo execute(String logId) {
		float minval = 0, maxval = 0;
		try {

			Map<String, Object> inputs = new HashMap<String, Object>();
			inputs.put(LOGID, logId);
			Map<?, ?> outParameters = super.execute(inputs);
			minval = ((Number) outParameters.get(MINVALUE)).floatValue();
			maxval = ((Number) outParameters.get(MAXVALUE)).floatValue();
		} catch (Exception e) {
			logger.error("Exception : " + e);
		}
		return new LogExtentsVo(minval, maxval);
	}

}
