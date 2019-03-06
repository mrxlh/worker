package com.collmall.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库工具类
 * @author  xulihui
 * @date 2019-03-06
 */
public class DbUtil {

	public static void closeConnection(Connection conn, Statement stmt) {
		try {
			if (null != stmt)
				stmt.close();
			if (null != conn && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
