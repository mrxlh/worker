package com.collmall.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 */
@Repository("baseDao")
public class BaseDao extends SqlSessionDaoSupport {

	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	public Connection getConnection() throws SQLException {
		return getSqlSession().getConfiguration().getEnvironment().getDataSource().getConnection();
	}

}
