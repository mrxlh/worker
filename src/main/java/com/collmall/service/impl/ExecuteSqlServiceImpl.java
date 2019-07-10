package com.collmall.service.impl;

import com.collmall.dao.BaseDao;
import com.collmall.exception.BusinessException;
import com.collmall.result.Result;
import com.collmall.service.ExecuteSqlService;
import com.collmall.util.DbUtil;
import com.collmall.util.SpringContext;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author: xulihui
 * @Date: 2019/3/6 11:30
 */
@Service
public class ExecuteSqlServiceImpl implements ExecuteSqlService {

    private static final Logger logger = LoggerFactory.getLogger(WorkerServiceImpl.class);

    @Autowired
    private BaseDao baseDao;

    @Override
    public Result<Integer> execute(String sql) {
        Connection conn = null;
        PreparedStatement pstmt1 = null;
//      SqlSessionFactory bean= SpringContext.getBean("sqlSessionTemplate");
        //  获取Mybatis sqlSession 工厂
        SqlSessionFactory bean= SpringContext.getBean("sqlSessionFactory");
        try {
            baseDao.setSqlSessionFactory(bean);
            conn = baseDao.getConnection();
            pstmt1=conn.prepareStatement(sql);
            pstmt1.execute();
            return new Result<>(1);
        } catch (SQLException e) {
            logger.error("执行失败", e);
            throw new BusinessException("sql执行失败");
        } catch (Exception e) {
            logger.error("执行失败", e);
            throw new BusinessException("sql执行失败");
        } finally {
            DbUtil.closeConnection(conn, pstmt1);
        }
    }
}
