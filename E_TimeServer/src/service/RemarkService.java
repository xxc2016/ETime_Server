package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.sql.ResultSet;


import bean.RemarkBean;
import database.JDBCConn;
import util.FileManager;

public class RemarkService {
	
	public PreparedStatement preparedStatement = null;
	public Statement statement = null;
	public ResultSet  resultSet = null;
	static private final Object  RemarkLock = new Object(); 
	
	public  RemarkBean upStoreRemarkService(String userId,int detailId,String time,String content,String date
			,List<String> imagePath,List<String> imagePath_src)
	{
		boolean  isSuccess = false;
		String sql_i_image  = "insert into RemarkPic(remarkId,picId,url)  values(?,?,?)";//评论图片
		String sql_i_pic = "insert into picSrcList(srcPic,compressPic) values(?,?)";//图片和压缩图
		String sql_q = " SELECT  SCOPE_IDENTITY() ";
		String sql_u = "update Post set remark = remark + 1 where detailId = ?";//增加评论
		synchronized(RemarkLock)
		{
			if(userId!=null)
			{
				userId = "'"+userId+"'";
			}
			if(content!=null)
			{
				content = "'"+content+"'";
			}
			if(time!=null)
			{
				time = "'"+time+"'";
			}
			if(date!=null)
			{
				date = "'"+date+"'";
			}
					
			String sql_i = " insert into Remark(userId,detailId,time,date,content) values("
					+ userId+","
					+ detailId+","
					+ time+","
					+ date+","
					+ content
					+ ")";  
			try
			{
				Connection conn = JDBCConn.getConnection();
				statement = conn.createStatement();
				
				statement.execute(sql_i);//插入remark
				resultSet = statement.executeQuery(sql_q);
				
				if(resultSet.next())//picId 从0开始
				{
					int remarkId = resultSet.getInt(1);
					for(int i=0;i<imagePath.size();i++)
					{
						preparedStatement = conn.prepareStatement(sql_i_image);//插入RemarkPic
						preparedStatement.setInt(1,remarkId);
						preparedStatement.setInt(2,i);//picId
						preparedStatement.setString(3,imagePath.get(i));
						preparedStatement.execute();
						
						
						preparedStatement = conn.prepareStatement(sql_i_pic);//插入picSrcList
						preparedStatement.setString(1, imagePath_src.get(i));
						preparedStatement.setString(2,imagePath.get(i));
						preparedStatement.execute();
						preparedStatement.close();
					}
					
				}
				
				preparedStatement = conn.prepareStatement(sql_u);
				preparedStatement.setInt(1,detailId);
				preparedStatement.execute();

				statement.close();
				conn.close();
				isSuccess = true;
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		RemarkBean  askRemarkBean = new RemarkBean();
		if(isSuccess)
		{
		  askRemarkBean.setResponseCode(RemarkBean.REMARK_UP_STORE_RESPONSE_SUCCESSED);
		}
		else
		{
			askRemarkBean.setResponseCode(RemarkBean.REMARK_UP_STORE_RESPONSE_FAILED);
		}
		return  askRemarkBean;
	}
	
	public  RemarkBean downLoadRemarkService(int remarkId)
	{
		boolean  isSuccess = false;
		String sql_q = "select * from remark where remarkId = ?";
		String sql_q_user = "select * from userImage where userId = ?";
		String sql_q_image = "select * from RemarkPic where remarkId = ?";
		RemarkBean  askRemarkBean = new RemarkBean();
		LinkedList<String> bitmapPath = new LinkedList<String>();
		askRemarkBean.setBitmapPath(bitmapPath);
		
		try
		{
			Connection conn = JDBCConn.getConnection();
			preparedStatement = conn.prepareStatement(sql_q);
			preparedStatement.setInt(1, remarkId);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
				String userId = resultSet.getString("userId");
				String time =  resultSet.getString("time");
				String date =  resultSet.getString("date");
				String content  = resultSet.getString("content");
				askRemarkBean.setTime(time);
				askRemarkBean.setContent(content);
				askRemarkBean.setDate(date);
				askRemarkBean.setRemarkId(remarkId);
				
				RemarkBean.User user= new RemarkBean.User();
				askRemarkBean.setUser(user);
				preparedStatement = conn.prepareStatement(sql_q_user);
				preparedStatement.setString(1, userId);
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next())
				{
					String nickName = resultSet.getString("userName");
					String head = resultSet.getString("userImagePath");
					user.account = userId;
					user.head = head;
					user.nickName = nickName;
					askRemarkBean.setUser(user);
				}
				
				preparedStatement = conn.prepareStatement(sql_q_image);//获得评论图片
				preparedStatement.setInt(1,remarkId);
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next())
				{
					String path = resultSet.getString("url");
					bitmapPath.add(path);
				}
				
				
				isSuccess = true;
			}
			
			preparedStatement.close();
			conn.close();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(isSuccess)
		{
		  askRemarkBean.setResponseCode(RemarkBean.REMARK_DOWN_LOAD_RESPONSE_SUCCESSED);
		}
		else
		{
			askRemarkBean.setResponseCode(RemarkBean.REMARK_DOWN_LOAD_RESPONSE_FAILED);
		}
		return  askRemarkBean;
	}
	
	public  RemarkBean deleteRemarkService(int remarkId)
	{
		boolean  isSuccess = false;
		String sql_d = "delete from Remark where remarkId = ?";
		String sql_d_image = "delete from RemarkPic where remarkId = ?";
		String sql_d_pic = "delete from picSrcList where compressPic = ?";
		String sql_q  ="select * from RemarkPic where remarkId = ?";
		String sql_q_pic = "select * from picSrcList where compressPic =?";
		RemarkBean  askRemarkBean = new RemarkBean();
		LinkedList<String> deletePic_compress = new LinkedList<String>();
		LinkedList<String> deletePic_src = new LinkedList<String>();
		
		try
		{
			Connection conn = JDBCConn.getConnection();

			preparedStatement = conn.prepareStatement(sql_q);
			preparedStatement.setInt(1, remarkId);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())//对于帖子的每个照片依次删除 查找每个原图
			{
				String compressPic =  resultSet.getString("srcPic");
				deletePic_compress.add(compressPic);
			}
			
			preparedStatement = conn.prepareStatement(sql_d_image);//删除RemarkPic
			preparedStatement.setInt(1,remarkId);
			preparedStatement.execute();
			
			for(int i=0;i<deletePic_compress.size();i++)
			{
				String compressPic = deletePic_compress.get(i);
				String srcPic =  null;
				preparedStatement = conn.prepareStatement(sql_q_pic);//查询srcPic
				preparedStatement.setString(1, compressPic);
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next())
				{
					srcPic = resultSet.getString("srcPic");
				}
				preparedStatement  = conn.prepareStatement(sql_d_pic);
				preparedStatement.setString(1,compressPic);
				preparedStatement.execute();
				deletePic_src.add(srcPic);
			}
			
			for(int i=0;i<deletePic_compress.size();i++)//删除图片
			{
				FileManager.deleteFile(deletePic_compress.get(i));
			}
			for(int i=0;i<deletePic_src.size();i++)
			{
				FileManager.deleteFile(deletePic_src.get(i));
			}
			
			
			preparedStatement = conn.prepareStatement(sql_d);//删除remark
			preparedStatement.setInt(1, remarkId);
			preparedStatement.execute();
			
			isSuccess = true;
			preparedStatement.close();
			conn.close();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(isSuccess)
		{
		  askRemarkBean.setResponseCode(RemarkBean.REMARK_DELETE_RESPONSE_SUCCESSED);
		}
		else
		{
			askRemarkBean.setResponseCode(RemarkBean.REMARK_DELETE_RESPONSE_FAILED);
		}
		return  askRemarkBean;
	}


}
