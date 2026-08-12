package org.auscope.nvcl.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import org.auscope.nvcl.server.vo.AveragedFloatDataVo;
import org.auscope.nvcl.server.dao.AbstractDownSampledDao;

@Repository
public class DownSampledFloatDataDao
        extends AbstractDownSampledDao<AveragedFloatDataVo> {

    public DownSampledFloatDataDao(
            DataSource dataSource,
            @Value("${jdbc.dbType}") String dbType) {

        super(
                dataSource,
                dbType,
                "LogTypeTwo",
                new LogTypeTwoRowMapper(),
                LogManager.getLogger(
                        DownSampledFloatDataDao.class));
    }

    private static class LogTypeTwoRowMapper
            implements RowMapper<AveragedFloatDataVo> {

        @Override
        public AveragedFloatDataVo mapRow(
                ResultSet rs,
                int rowNum)
                throws SQLException {

            AveragedFloatDataVo vo =
                    new AveragedFloatDataVo();

            vo.setRoundedDepth(
                    rs.getFloat("ROUNDEDDEPTH"));

            vo.setAverageValue(
                    rs.getFloat("AVERAGEVALUE"));

            return vo;
        }
    }
}