package com.jst.vesms.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jst.constant.Message;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import oracle.jdbc.OracleTypes;

/**
 * 用于数据库链接的操作
 * 
 * @author DanRui
 *
 */
public class DBConnUtil {

	/**
	 * 默认数据源
	 */
	public static ComboPooledDataSource cp = null; // 默认数据源

	/**
	 * 其它数据源 对应的是c3p0-config.xml中的other-datasource数据源配置
	 */
	public static ComboPooledDataSource other = null; // 其它数据源

	/**
	 * 用于记录日志
	 */
	private static final Log log = LogFactory.getLog(DBConnUtil.class);

	static {
		 inintConn();
	}

	/**
	 * 初始化数据信息
	 */
	public static void inintConn() {
		inintDefault();
		inintOtherSource();
	}

	/**
	 * 初始化默认数据源
	 */
	public static void inintDefault() {
		try {
			cp = new ComboPooledDataSource();
		} catch (Exception e) {
			log.debug("DBConnUtil inintDefault dataSource is Error:" + e, e);
		}
	}

	/**
	 * 初始化其它数据源
	 */
	public static void inintOtherSource() {
		try {
			other = new ComboPooledDataSource("other-datasource");
		} catch (Exception e) {
			log.debug("DBConnUtil inintDefault dataSource is Error:" + e, e);
		}
	}

	/**
	 * 根据分页获取该页下的学员综合信息
	 * 
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	public static Map<String, String> loadStudentInfoByPage(Integer pageSize, Integer pageNum) {
		ResultSet rs = null;
		CallableStatement st = null;
		Connection conn = null;
		log.debug("DBConnUitl loadStudentInfoByPage is start");
		String result = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			// 判断数据源是否已经加载
			if (cp == null) {
				inintDefault();
			}
			conn = cp.getConnection();
			st = conn.prepareCall("{call PKG_STUDENT.p_get_stu_portal_list(?,?,?,?,?,?)}");
			st.setString(1, "1");
			st.setInt(2, 1);
			st.setInt(3, 50);
			st.registerOutParameter(4, OracleTypes.VARCHAR);
			st.registerOutParameter(5, OracleTypes.VARCHAR);
			st.registerOutParameter(6, OracleTypes.CURSOR);
			st.executeQuery();
			rs = (ResultSet) st.getObject(6);
			String retCode = st.getString(4);
			String retMsg = st.getString(5);
			while (rs.next()) {

				map.put(rs.getString("ID_CARD"), rs.getString("JSON_STR"));
				// RedisUtil.setValue(RedisUtil.STU_INFO,
				// rs.getString("ID_CARD"), rs.getString("JSON_STR"));
			}

		} catch (Exception e) {
			log.error("DBConnUtil loadStudentInfoByPage is error" + e, e);
		} finally {
			closeAll(rs, st, conn);
		}
		log.debug("DBConnUtil loadStudentInfoByPage is end");
		return map;
	}

	/**
	 * 查询满足条件的学员总数
	 * 
	 * @param type
	 * @return
	 */
	public static Map<String, Object> loadStudentInfoCount(String type) {
		CallableStatement st = null;
		Connection conn = null;
		Map<String, Object> map = new HashMap<String, Object>();
		log.debug("DBConnUtil loadStudentInfoCount is start");

		try {
			if (null == cp) {
				inintDefault();
			}
			conn = cp.getConnection();
			st = conn.prepareCall("{call PKG_STUDENT.p_get_stu_portal_count(?,?,?,?,?,?)}");
			st.setString(1, type);
			st.registerOutParameter(2, OracleTypes.VARCHAR);
			st.registerOutParameter(3, OracleTypes.VARCHAR);
			st.registerOutParameter(4, OracleTypes.NUMBER);
			st.registerOutParameter(5, OracleTypes.NUMBER);
			st.registerOutParameter(6, OracleTypes.NUMBER);
			st.executeQuery();
			String retCode = st.getString(2);
			String retMsg = st.getString(3);
			Integer totalCount = st.getInt(4);
			Integer pageSize = st.getInt(5);
			Integer pageCount = st.getInt(6);
			map.put(Message.RET_CODE_NAME, retCode);
			map.put(Message.RET_MSG_NAME, retMsg);
			map.put("totalCount", totalCount);
			map.put("pageSize", pageSize);
			map.put("pageCount", pageCount);
		} catch (Exception e) {

			log.error("DBConnUtil loadStudentInfoCount is Error:" + e, e);
		} finally {
			closeAll(null, st, conn);
		}

		log.debug("DBConnUtil loadStudentInfocount is end");

		return map;
	}

	/**
	 * 根据身份证重新加载该学员的缓存
	 * 
	 * @param idCard
	 * @return
	 */
	public static String loadStudentInfoByIdCard(String idCard) {
		ResultSet rs = null;
		CallableStatement st = null;
		Connection conn = null;
		String result = null;
		log.debug("DBConnUtil loadStudentInfoByIdCard is start");
		String sql = "";
		try {
			if (cp == null) {
				inintDefault();
			}
			conn = cp.getConnection();
			st = conn.prepareCall(sql);
			rs = st.executeQuery();
			while (rs.next()) {
				result = rs.getString(0);
			}
		} catch (Exception e) {
			log.debug("DBConnUtil loadStudentInfoByIdCard is Error:" + e, e);
		} finally {
			closeAll(rs, st, conn);
		}
		log.debug("DBConnUtil loadStudentInfoByIdCard is end");
		return result;
	}

	/**
	 * 关闭当前的数据库操作信息
	 * 
	 * @param rs
	 * @param st
	 * @param conn
	 */
	public static void closeAll(ResultSet rs, Statement st, Connection conn) {
		log.debug("DBConnUtil closeAll is start");
		try {
			if (null != rs) {
				rs.close();
			}

			if (null != st) {
				st.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			log.error("DbConnUtil closeAll is Error" + e, e);
		}
		log.debug("DBConnUtil closeAll is end");

	}

	/**
	 * 根据传入字符串输出函数返回结果
	 * 
	 * @param msg
	 * @return
	 */
	public static String loadMsgFromDB(String msg) {
		ResultSet rs = null;
		CallableStatement st = null;
		Connection conn = null;
		log.debug("DBConnUitl loadMsgFromDB is start");
		String result = null;
		try {
			// 判断数据源是否已经加载
			if (cp == null) {
				inintDefault();
			}
			conn = cp.getConnection();
			st = conn.prepareCall("{?=call PKG_TEST.test_msg(?)}");
			st.setString(2, msg);
			st.registerOutParameter(1, OracleTypes.VARCHAR);
			st.executeQuery();
			result = st.getString(1);
		} catch (Exception e) {
			log.error("DBConnUtil loadMsgFromDB is error" + e, e);
		} finally {
			closeAll(rs, st, conn);
		}
		log.debug("DBConnUtil loadMsgFromDB is end");
		return result;
	}

	public static void main(String[] args) {
		DBConnUtil db = new DBConnUtil(); 
		Properties p = new Properties();
		Class clazz = DBConnUtil.class;
		ClassLoader loader = db.getClass().getClassLoader();
		//getResourceAsStream
		//loader.getResourceAsStream(path);
		//当前类编译的文件所在包目录
		System.out.println(clazz.getResource(""));
		//classpath	根目录
		System.out.println(clazz.getResource("/"));
		System.out.println(loader.getResource(""));
		 InputStream in = clazz.getResourceAsStream("/c3p0-config.xml");
		 try {
			p.load(in);
			System.out.println(DBConnUtil.loadMsgFromDB("Hello,Oracle"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// p.load(DBConnUtil.class.getResourceAsStream("/c3p0.properties"));
	}
}
