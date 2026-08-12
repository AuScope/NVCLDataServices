package org.auscope.nvcl.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import org.auscope.nvcl.server.vo.BinnedClassDataVo;
import org.auscope.nvcl.server.dao.AbstractDownSampledDao;


@Repository
public class DownSampledClassDataDao
        extends AbstractDownSampledDao<BinnedClassDataVo> {

    public DownSampledClassDataDao(
            DataSource dataSource,
            @Value("${jdbc.dbType}") String dbType) {

        super(
                dataSource,
                dbType,
                "LogTypeOne",
                new LogTypeOneRowMapper(),
                LogManager.getLogger(
                        DownSampledClassDataDao.class));
    }

    private static class LogTypeOneRowMapper
            implements RowMapper<BinnedClassDataVo> {

        @Override
        public BinnedClassDataVo mapRow(
                ResultSet rs,
                int rowNum)
                throws SQLException {

            BinnedClassDataVo vo =
                    new BinnedClassDataVo();

            vo.setRoundedDepth(
                    rs.getFloat("ROUNDEDDEPTH"));

            vo.setClassCount(
                    rs.getInt("CLASSCOUNT"));

            vo.setClassText(
                    rs.getString("CLASSTEXT"));

            vo.setColour(
                    rs.getInt("COLOUR"));

            return vo;
        }
    }
}