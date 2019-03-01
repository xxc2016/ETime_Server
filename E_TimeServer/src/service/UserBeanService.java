package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import bean.UserBean;
import database.JDBCConn;

public class UserBeanService {
	
	public PreparedStatement preparedStatement = null;
	public Statement statement = null;
	public ResultSet  resultSet = null;
	
	public  UserBean upStoreUserBeanFollowListService(String userId,List<String> list)
	{	
		boolean isSuccess = false;
		String sql_i = "insert into followList(userId_head,userId_rear) values(?,?)";
		try
		{
			Connection conn = JDBCConn.getConnection();
			for(int i=0;i<list.size();i++)
			{
				String userId_f= list.get(i);
				preparedStatement =  conn.prepareStatement(sql_i);
				preparedStatement.setString(1, userId);
				preparedStatement.setString(2, userId_f);
				preparedStatement.execute();
			}
			preparedStatement.close();
			conn.close();
			isSuccess = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		UserBean  askUserBean = new UserBean();
		if(isSuccess)
		{
		  askUserBean.setResponseCode(UserBean.USER_UP_FOLLOWLIST_RESPONSE_SUCCESSED);
		}
		else
		{
			askUserBean.setResponseCode(UserBean.USER_UP_FOLLOWLIST_RESPONSE_FAILED);
		}
		return  askUserBean;
	}
	
	public  UserBean downLoadUserBeanService(String userId)
	{	
		boolean isSuccess = false;
		String sql_q_user = "select * from userImage where userId = ?";
		String sql_q_follow = "select * from followList where userId_head = ?";
		String sql_q_remark = "select * from Remark where userId = ?";
		String sql_q_postDetail = "select * from PostDetail where userId = ?";
		String sql_q_post = "select * from Post where detailId = ?";
		
		UserBean  askUserBean = new UserBean();
		LinkedList<String> followList = new LinkedList<String>();
		LinkedList<Integer> remarkList = new LinkedList<Integer>();
		LinkedList<Integer> postList = new LinkedList<Integer>();
		askUserBean.setFollowList(followList);
		askUserBean.setRemarkList(remarkList);
		askUserBean.setPostList(postList);
		
		try
		{
			Connection conn = JDBCConn.getConnection();
			preparedStatement = conn.prepareStatement(sql_q_user);
			preparedStatement.setString(1,userId);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())//查询用户信息
			{
				String head = resultSet.getString("userImagePath");
				String nickName = resultSet.getString("userName");
				askUserBean.setAccount(userId);
				askUserBean.setNickName(nickName);
				askUserBean.setHead(head);
			}
			
			preparedStatement = conn.prepareStatement(sql_q_follow);
			preparedStatement.setString(1,userId);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())//查询关注列表
			{
				String user_f = resultSet.getString("userId_rear");
				followList.add(user_f);
			}
			
			preparedStatement = conn.prepareStatement(sql_q_remark);
			preparedStatement.setString(1,userId);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())//查询评论列表
			{
				int remarkId = resultSet.getInt("remarkId");
			    remarkList.add(remarkId);
			}
			
			
			preparedStatement = conn.prepareStatement(sql_q_postDetail);
			preparedStatement.setString(1,userId);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())//查询发帖列表
			{
				int PostDetailId = resultSet.getInt("detailId");
			    
				PreparedStatement statement = conn.prepareStatement(sql_q_post);
				statement.setInt(1,PostDetailId);
				ResultSet result = statement.executeQuery();
				if(result.next())
				{
					int postId = result.getInt("postId");
					postList.add(postId);
				}
				
				statement.close();
			}
			
			preparedStatement.close();
			conn.close();
			isSuccess = true;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(isSuccess)
		{
		  askUserBean.setResponseCode(UserBean.USER_DOWN_LOAD_RESPONSE_SUCCESSED);
		}
		else
		{
			askUserBean.setResponseCode(UserBean.USER_DOWN_LOAD_RESPONSE_FAILED);
		}
		return  askUserBean;
	
	}
	
	
	public  UserBean deleteUserBeanFollowListService(String userId,List<String> list)
	{	
		boolean isSuccess = false;
		String sql_d = "delete from followList where (userId_head = ? and userId_rear = ?)";
		try
		{
			Connection conn = JDBCConn.getConnection();
			for(int i=0;i<list.size();i++)
			{
				String userId_f= list.get(i);
				preparedStatement =  conn.prepareStatement(sql_d);
				preparedStatement.setString(1, userId);
				preparedStatement.setString(2, userId_f);
				preparedStatement.execute();
			}
			preparedStatement.close();
			conn.close();
			isSuccess = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		UserBean  askUserBean = new UserBean();
		if(isSuccess)
		{
		  askUserBean.setResponseCode(UserBean.USER_DELETE_FOLLOWLIST_RESPONSE_SUCCESSED);
		}
		else
		{
			askUserBean.setResponseCode(UserBean.USER_DELETE_FOLLOWLIST_RESPONSE_FAILED);
		}
		return  askUserBean;
	}
	
//	public  UserBean askUserRelationShip(String userId_head,String userId_rear)
//	{	
//		boolean isSuccess = false;
//		String sql_q = "select * from followList where (userId_head = ? and userId_rear = ?)";
//		try
//		{
//			Connection conn = JDBCConn.getConnection();
//			preparedStatement =  conn.prepareStatement(sql_q);
//			preparedStatement.setString(1, userId_head);
//			preparedStatement.setString(2, userId_rear);
//			boolean result = preparedStatement.execute();
//			preparedStatement.close();
//			conn.close();
//			isSuccess = true;
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//		UserBean  askUserBean = new UserBean();
//		if(isSuccess)
//		{
//		  askUserBean.setResponseCode(UserBean.USER_DELETE_FOLLOWLIST_RESPONSE_SUCCESSED);
//		}
//		else
//		{
//			askUserBean.setResponseCode(UserBean.USER_DELETE_FOLLOWLIST_RESPONSE_FAILED);
//		}
//		return  askUserBean;
//	}

}
