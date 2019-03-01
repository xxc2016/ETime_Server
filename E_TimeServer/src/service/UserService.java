package service;
import java.sql.*;
import bean.User;
import database.JDBCConn;

public class UserService{
	
	 public PreparedStatement preparedStatement = null;
	 public ResultSet  resultSet = null;
	 public int login_state = 0;
	 public User user  =new User();
	
	public   User loginService(String userId,String password)
	{
		boolean  isSuccess = false;
		String sql_q = "select * from userList where userId = ? and password = ?";
		try
		{
			Connection conn = JDBCConn.getConnection();
			preparedStatement = conn.prepareStatement(sql_q);
			preparedStatement.setString(1,userId);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
				System.out.println("id:"+resultSet.getString(1)
				+"password:"+resultSet.getString(2));
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
		askUser.setPassword(password);
		if(isSuccess)
		{
		  askUser.setResponseCode(User.LOGIN_RESPONSE_SUCCESSED);
		}
		else
		{
		  askUser.setResponseCode(User.LOGIN_RESPONSE_FAILED);
		}
		return  askUser;
	}
	
	public  User registerService(String userId,String password)
	{
		boolean isSuccess = false;
		String sql_q = "select * from userList where userId = ?";
		String sql_i = "insert into userList values(?,?)";
		String sql_i_image = "insert into userImage (userId,userName,userImagePath) values(?,?,?)";
		
		try
		{
			Connection conn= JDBCConn.getConnection();
			preparedStatement = conn.prepareStatement(sql_q);
			preparedStatement.setString(1, userId);
			resultSet = preparedStatement.executeQuery();
			if(!resultSet.next())
			{
				preparedStatement = conn.prepareStatement(sql_i);
				preparedStatement.setString(1,userId);
				preparedStatement.setString(2,password);
				preparedStatement.execute();
				
				preparedStatement = conn.prepareStatement(sql_i_image);
				preparedStatement.setString(1,userId);
				preparedStatement.setString(2,null);
				preparedStatement.setString(3,null);
				preparedStatement.execute();//用户信息注册
				
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
		askUser.setPassword(password);
		if(isSuccess)
		{
		  askUser.setResponseCode(User.REGISTER_RESPONSE_SUCCESSED);
		}
		else
		{
		  askUser.setResponseCode(User.REGISTER_RESPONSE_FAILED);
		}
		return  askUser;
	}

}
