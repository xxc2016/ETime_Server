package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.User;
import database.JDBCConn;
import util.FileManager;

public class UserImageService {
	
	public PreparedStatement preparedStatement = null;
	public ResultSet  resultSet = null;
	public int login_state = 0;
	public User user  =new User();
	
	public   User imageStoreService(String userId,String fileName,String fileName_src)
	{
		boolean  isSuccess = false;
		String sql_q = "select * from userImage where userId = ?";
		String sql_i = "update  userImage"
				+ " set userImagePath = ? "
				+ " where userId = ?";  
		String sql_i_pic =" insert into picSrcList(srcPic,compressPic)  values(?,?)"; 
		String sql_q_src = "select * from picSrcList where compressPic = ?";
		String sql_d  = "delete  from picSrcList where compressPic = ?";
		
		
		try
		{
			Connection conn = JDBCConn.getConnection();
			preparedStatement = conn.prepareStatement(sql_q);
			preparedStatement.setString(1,userId);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
				String compressPic = resultSet.getString("userImagePath");
				String srcPic = null;
				
				if(compressPic!=null)
				{
					preparedStatement = conn.prepareStatement(sql_q_src);
					preparedStatement.setString(1,compressPic);
					resultSet = preparedStatement.executeQuery();
					
					if(resultSet.next())
					{
						srcPic = resultSet.getString("srcPic");
					}
					
					preparedStatement = conn.prepareStatement(sql_d);
					preparedStatement.setString(1,compressPic);
					preparedStatement.execute();
					
					FileManager.deleteFile(compressPic);
					FileManager.deleteFile(srcPic);		
					
				}
				
		        preparedStatement = conn.prepareStatement(sql_i);
		        preparedStatement.setString(1, fileName);
		        preparedStatement.setString(2, userId);
		        preparedStatement.execute();
		        
		        
		        
		        preparedStatement = conn.prepareStatement(sql_i_pic);
		        preparedStatement.setString(1,fileName_src);
		        preparedStatement.setString(2,fileName);
		        preparedStatement.execute();
				isSuccess = true;
			}
			
			preparedStatement.close();
			conn.close();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		User  askUser = new User();
		askUser.setName(userId);
		askUser.setImagePath(fileName);
		if(isSuccess)
		{
		  askUser.setResponseCode(User.IMAGE_STORE_RESPONSE_SUCCESSED);
		}
		else
		{
		  askUser.setResponseCode(User.IMAGE_DOWNLOAD_RESPONSE_FAILED);
		}
		return  askUser;
	}
	
	
	
	public  User imageDownLoadService(String userId)
	{
		boolean  isSuccess = false;
		String sql_q = "select * from userImage where userId = ?";
		User  askUser = new User();
		try
		{
			Connection conn = JDBCConn.getConnection();
			preparedStatement = conn.prepareStatement(sql_q);
			preparedStatement.setString(1,userId);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
		        askUser.setNickName(resultSet.getString("userName"));
		        askUser.setImagePath(resultSet.getString("userImagePath"));
				isSuccess = true;
			}
			
			preparedStatement.close();
			conn.close();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		askUser.setName(userId);
		if(isSuccess)
		{
		  askUser.setResponseCode(User.IMAGE_DOWNLOAD_RESPONSE_SUCCESSED);
		}
		else
		{
		  askUser.setResponseCode(User.IMAGE_DOWNLOAD_RESPONSE_FAILED);
		}
		return  askUser;
	}
	
	public   User imageStoreExtraService(String userId,String userName)
	{
		boolean  isSuccess = false;
		String sql_q = "select * from userImage where userId = ?";
		String sql_i = "update  userImage"
				+ " set userName = ?"
				+ " where userId = ?";
		try
		{
			Connection conn = JDBCConn.getConnection();
			preparedStatement = conn.prepareStatement(sql_q);
			preparedStatement.setString(1,userId);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
		        preparedStatement = conn.prepareStatement(sql_i);
		        preparedStatement.setString(1,userName);
		        preparedStatement.setString(2, userId);
		        preparedStatement.execute();
				isSuccess = true;
			}
			
			preparedStatement.close();
			conn.close();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		User  askUser = new User();
		askUser.setName(userId);
		askUser.setNickName(userName);
		if(isSuccess)
		{
		  askUser.setResponseCode(User.IMAGE_STORE_EXTRA_RESPONSE_SUCCESSED);
		}
		else
		{
		  askUser.setResponseCode(User.IMAGE_STORE_EXTRA_RESPONSE_FAILED);
		}
		return  askUser;
	}
	

}
