package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bean.TraceBean;
import bean.TraceBean.Trace;
import database.JDBCConn;

public class TracesService {
	
	public PreparedStatement preparedStatement = null;
	public ResultSet  resultSet = null;
	public int login_state = 0;
	public TraceBean traceBean = new TraceBean();
	
	public   TraceBean upStoreTraceService(String userId,List<TraceBean.Trace> list)
	{
		boolean  isSuccess = false;
		String sql_q = "select * from userList where userId = ?";
		String sql_d = "delete from userTrace where userId = ? ";
		String sql_i = " insert into userTrace values(?,?,?,?,?,?,?,?,?,?,?)";  
		
		if(list!=null)
		{
			try
			{
				Connection conn = JDBCConn.getConnection();
				preparedStatement = conn.prepareStatement(sql_q);
				preparedStatement.setString(1,userId);
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next())
				{
					preparedStatement = conn.prepareStatement(sql_d);
					preparedStatement.setString(1,userId);
					preparedStatement.execute();
					
					Iterator<Trace> it = list.iterator();
					int count =0;
					while(it.hasNext())
					{
						count++;
						System.out.println(count);
						TraceBean.Trace trace = it.next();
				        preparedStatement = conn.prepareStatement(sql_i);
				        preparedStatement.setString(1, userId);
				        preparedStatement.setString(2, trace.time);
				        preparedStatement.setString(3, trace.event);
				        preparedStatement.setString(4, trace.date);
				        preparedStatement.setBoolean(5, trace.finish);
				        preparedStatement.setInt(6, trace.traceId);
				        preparedStatement.setInt(7, trace.imageType);
				        preparedStatement.setBoolean(8, trace.important);
				        preparedStatement.setBoolean(9, trace.urgent);
				        preparedStatement.setBoolean(10, trace.fix);
				        preparedStatement.setInt(11, trace.predict);
				        preparedStatement.execute();					
					}
					isSuccess = true;
				}
				
				preparedStatement.close();
				conn.close();
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		TraceBean  askTraceBean = new TraceBean();
		askTraceBean.setUserAccount(userId);
		if(isSuccess)
		{
		  askTraceBean.setResponseCode(TraceBean.UP_STORE_RESPONSE_SUCCESSED);
		}
		else
		{
		  askTraceBean.setResponseCode(TraceBean.UP_STORE_RESPONSE_FAILED);
		}
		return  askTraceBean;
	}
	
	public   TraceBean downLoadTraceService(String userId,List<TraceBean.Trace> list)
	{
		boolean  isSuccess = false;
		String sql_q = "select * from userList where userId = ?";
		String sql_q_trace = "select * from userTrace where userId = ? "; 
		ArrayList<TraceBean.Trace> traces = new ArrayList<TraceBean.Trace>();
		try
		{
			Connection conn = JDBCConn.getConnection();
			preparedStatement = conn.prepareStatement(sql_q);
			preparedStatement.setString(1,userId);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
				preparedStatement = conn.prepareStatement(sql_q_trace);
				preparedStatement.setString(1,userId);
				resultSet = preparedStatement.executeQuery();
				
				while(resultSet.next())
				{
					String userId_q = resultSet.getString("userId");
					String time_q = resultSet.getString("time");
					String event_q = resultSet.getString("event");
					String date_q = resultSet.getString("date");
					Boolean finish_q = resultSet.getBoolean("finish");
					int traceId_q = resultSet.getInt("traceId");
					Boolean important_q = resultSet.getBoolean("important");
					Boolean urgent_q = resultSet.getBoolean("urgent");
					Boolean fix_q = resultSet.getBoolean("fix");
					int predict_q = resultSet.getInt("predict");
					TraceBean.Trace trace = new TraceBean.Trace(userId_q, time_q, event_q, date_q,
							finish_q, traceId_q, important_q, urgent_q, fix_q, predict_q);
					traces.add(trace);
				}

				isSuccess = true;
			}
			
			preparedStatement.close();
			conn.close();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		TraceBean  askTraceBean = new TraceBean();
		askTraceBean.setUserAccount(userId);
		askTraceBean.setTraces(traces);
		if(isSuccess)
		{
		  askTraceBean.setResponseCode(TraceBean.DOWN_LOAD_REUQEST_SUCCESSED);
		}
		else
		{
		  askTraceBean.setResponseCode(TraceBean.DOWN_LOAD_REQUEST_FAILED);
		}
		return  askTraceBean;
	}

}
