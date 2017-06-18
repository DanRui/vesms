
package com.jst.vesms.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.jdbc.OracleTypes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import com.jst.vesms.dao.ICallDao;

@Repository("callDao")
public class CallDao implements ICallDao {
	
	private static final Log log = LogFactory.getLog(CallDao.class);
	
	@Resource
	private SessionFactory sessionFactory;

	@Override
	public List<List<Map<String, Object>>> callForMultiRS(final String name, final Map<Integer, Object> inParams,
			final Map<Integer, Integer> outParams, final String type) throws Exception {
		
			final List<List<Map<String, Object>>> result = new ArrayList<List<Map<String, Object>>>();
			Session session = sessionFactory.getCurrentSession();
			try {
				session.doWork(new Work() {
		
					@Override
					public void execute(Connection conn) throws SQLException {
						
						CallableStatement callStat = null;
						ResultSet rs = null;
						callStat = conn.prepareCall(name);
						
						//设置输入参数值
						for (Map.Entry<Integer, Object> entry : inParams.entrySet()) {
							callStat.setObject(entry.getKey(), entry.getValue());
						}
						//设置输出参数类型
						Iterator<Map.Entry<Integer, Integer>> it = outParams.entrySet().iterator();
						while(it.hasNext()) {
							Map.Entry<Integer, Integer> entry = it.next();
							callStat.registerOutParameter(entry.getKey(), entry.getValue());
						}
						boolean hasResult = callStat.execute();//执行存储过程
						
						ResultSetMetaData rsMetaData = null; 
						
						while (hasResult) {
							List<Map<String, Object>> rsList = new ArrayList<Map<String, Object>>();
							rs = callStat.getResultSet();//取得当前结果集
							rsMetaData = rs.getMetaData();
                            int colCount = rsMetaData.getColumnCount();//获取当前结果集的列数
	                        while(rs.next()) {
	                            Map<String, Object> map = new HashMap<String, Object>();
	                             for(int i = 1 ; i <= colCount ; i ++) {
	                                 String colName = rsMetaData.getColumnName(i);//列名称
	                                 map.put(colName, rs.getObject(colName));//将列名称和值放入Map
	                            }
	                             rsList.add(map);
	                        }
	                        result.add(rsList);
	                        close(null, rs);//遍历完一个结果集，将其关闭
	                        hasResult = callStat.getMoreResults();//移到下一个结果集
						}
						close(callStat, rs);//关闭CallableStatement和ResultSet
					}
				});
				return result;
			} catch (Exception e) {
				log.error("CallDao call error:"+e, e);
			} 
			/*DataSource ds = SessionFactoryUtils.getDataSource(session.getSessionFactory());
			Connection conn = ds.getConnection();*/
			return null;
		}

	
	
	private void close(CallableStatement cs, ResultSet rs) {
         try {
             if(cs != null) {
                 cs.close();
             }
             if(rs != null) {                 
            	rs.close();
            }
       } catch (Exception e) {
             e.printStackTrace();
         }
    }



	@Override
	public List<Map<String, Object>> call(final String name, final Map<Integer, Object> inParams,
			final Map<Integer, Integer> outParams, final String type) throws Exception {
			
			final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			Session session = sessionFactory.getCurrentSession();
			try {
				session.doWork(new Work() {
		
					@Override
					public void execute(Connection conn) throws SQLException {
						
						CallableStatement callStat = null;
						ResultSet rs = null;
						callStat = conn.prepareCall(name);
						
						//是否返回游标类型
						boolean hasCursorResult = false;
						
						//设置输入参数值
						if (null != inParams && !inParams.isEmpty()) {
							for (Map.Entry<Integer, Object> entry : inParams.entrySet()) {
								callStat.setObject(entry.getKey(), entry.getValue());
							}
						}
						//设置输出参数类型
						if (null != outParams && !outParams.isEmpty()) {
							Iterator<Map.Entry<Integer, Integer>> it = outParams.entrySet().iterator();
							while(it.hasNext()) {
								Map.Entry<Integer, Integer> entry = it.next();
								if (entry.getValue() == OracleTypes.CURSOR) {
									hasCursorResult = true;
								}
								callStat.registerOutParameter(entry.getKey(), entry.getValue());
							}
						}
							
						if (hasCursorResult) { //返回简单游标类型，遍历结果集
							// 这里只返回一个结果集，返回多个结果集使用上一个方法
							callStat.execute();
							// 只有一个结果集才会执行，否则跳过返回空值
							if (outParams.keySet().size() == 1) {
								rs = (ResultSet) callStat.getObject(outParams.keySet().iterator().next());
								ResultSetMetaData rsMetaData = rs.getMetaData();
	                            int colCount = rsMetaData.getColumnCount();//获取当前结果集的列数
								while(rs.next()) {
									Map<String, Object> map = new HashMap<String, Object>();
									for(int i = 1 ; i <= colCount ; i ++) {
										String colName = rsMetaData.getColumnName(i);//列名称
										map.put(colName, rs.getObject(colName));//将列名称和值放入Map
		                            }
									result.add(map);
								}
							}
						} else { //返回普通参数的，构造Map对象，返回List
							rs = callStat.executeQuery();//执行
							//循环输出参数得到输出参数类型和值，放入Map
							Map<String, Object> tmp = new HashMap<String, Object>();
							for (Map.Entry<Integer, Integer> entry : outParams.entrySet()) {
								tmp.put(entry.getKey().toString(), callStat.getObject(entry.getKey()));
							}
							result.add(tmp);
						}
						close(callStat, rs);//关闭CallableStatement和ResultSet
					}
				});
				return result;
			} catch (Exception e) {
				log.error("CallDao call error:"+e, e);
				throw new Exception(e);
			} 
			//return null;
	}


}

