package service;


import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.sql.ResultSet;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;

import bean.PostDetailBean;
import bean.TraceBean;
import bean.TraceBean.Trace;
import database.JDBCConn;

public class PostDetailService {
	
	public Statement Statement = null;//妥协  从preparedStatement  变成Statement  绕过不能改变sql语句的问题
	public PreparedStatement preparedStatement = null;
	public ResultSet  resultSet = null;
	static private final Object  PostLock = new Object(); 
	
	public  PostDetailBean upStorePostDetailService(String userId,String title,String content,String time
			,String pic,String date,List<String> imagePath,List<String> imagePath_src)
	{
		String sql_i_image = "insert into PostDetailPic(detailId,picId,url) values(?,?,?)";
		String sql_i_pic = "insert into picSrcList(srcPic,compressPic)  values(?,?)";
		synchronized(PostLock)
		{
			boolean  isSuccess = false;
			
			if(userId!=null)
			{
				userId = "'"+userId+"'";
			}
			if(title!=null)
			{
				title = "'"+title+"'";
			}
			if(content!=null)
			{
				content = "'"+content+"'";
			}
			if(time!=null)
			{
				time = "'"+time+"'";
			}
			if(pic!=null)
			{
				pic = "'"+pic+"'";
			}
			if(date!=null)
			{
				date = "'"+date+"'";
			}
				
			String sql_i = " insert into PostDetail(userId,title,pic,time,content,date)"
					+ " values( "+userId+","
							+ title + ","
							+pic+ ","
							+time+","
							+content+","
							+date+")";
			String sql_q = " SELECT  SCOPE_IDENTITY() ";
			
			
			Connection conn = JDBCConn.getConnection();
			try
			{
				System.out.println(sql_i);
				Statement = conn.createStatement();
			    Statement.execute(sql_i);
			    resultSet = Statement.executeQuery(sql_q);
			    int index;
			    int watch = 0;
			    int remark = 0;
		
				if(resultSet.next())
				{
					index = resultSet.getInt(1);
					System.out.println("index:"+index);
					String sql_i_detail = "insert into Post(detailId,watch,remark) values("
							+index+","
							+watch+","
							+remark+")";
					Statement.execute(sql_i_detail);
					
					if(imagePath!=null)
					{
						for(int i=0;i<imagePath.size();i++)
						{
							String picPath = imagePath.get(i);
							String picPath_src = imagePath_src.get(i);
							preparedStatement = conn.prepareStatement(sql_i_image);
							preparedStatement.setInt(1, index);
							preparedStatement.setInt(2, i);
							preparedStatement.setString(3, picPath);
							preparedStatement.execute();
			
							preparedStatement = conn.prepareStatement(sql_i_pic);
							preparedStatement.setString(1,picPath_src);
							preparedStatement.setString(2,picPath);
							preparedStatement.execute();
							preparedStatement.close();
							
						}
					}
					
				}
				
				
				isSuccess = true;
				Statement.close();
				conn.close();
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			PostDetailBean  askPostDetailBean = new PostDetailBean();
			if(isSuccess)
			{
			  askPostDetailBean.setResponseCode(PostDetailBean.POST_DETAIL_UP_STORE_RESPONSE_SUCCESSED);
			}
			else
			{
			  askPostDetailBean.setResponseCode(PostDetailBean.POST_DETAIL_UP_STORE_RESPONSE_FAILED);
			}
			return  askPostDetailBean;
		}
	}
	
	public  PostDetailBean downLoadPostDetailService(int DetailId)
	{
		boolean  isSuccess = false;
		String sql_q = "select * from PostDetail where detailId = ?";
		String sql_q_user = "select * from userImage where userId = ?";
		String sql_q_remark = "select * from Remark where  detailId = ?";
		String sql_q_image = "select * from PostDetailPic where detailId = ? order by picId asc";
		String sql_u = "update Post set watch = watch+1  where detailId = ?";
		String sql_q_post = "select * from Post where detailId = ?";
		String sql_q_remark_image = "select * from RemarkPic where remarkId = ?";
		PostDetailBean  askPostDetailBean = new PostDetailBean();
		
		try
		{
			Connection conn = JDBCConn.getConnection();
			preparedStatement = conn.prepareStatement(sql_u);
			preparedStatement.setInt(1, DetailId);
			preparedStatement.execute();//观看数加一
			
			
			preparedStatement = conn.prepareStatement(sql_q);
			preparedStatement.setInt(1,DetailId);
			
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())//获得postDetail
			{
				String userId = resultSet.getString("userId");
				String title = resultSet.getString("title");
				String pic = resultSet.getString("pic");
				String time = resultSet.getString("time");
				String date = resultSet.getString("date");
				String content = resultSet.getString("content");
				
				askPostDetailBean.setDetailId(DetailId);
				askPostDetailBean.setTitle(title);
				askPostDetailBean.setTime(time);
				askPostDetailBean.setContent(content);
				askPostDetailBean.setDate(date);
				
				PostDetailBean.Remark.User user = new PostDetailBean.Remark.User();
				askPostDetailBean.user = user;
				LinkedList<PostDetailBean.Remark> remarkList = new LinkedList<PostDetailBean.Remark>();
				askPostDetailBean.setRemarkList(remarkList);
				LinkedList<String>  bitmapPath = new LinkedList<String>();
				askPostDetailBean.setBitmapPath(bitmapPath);
				
				preparedStatement = conn.prepareStatement(sql_q_image);
				preparedStatement.setInt(1,DetailId);
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next())
				{
					String picPath = resultSet.getString("url");
					bitmapPath.add(picPath);
				}
				
				preparedStatement = conn.prepareStatement(sql_q_post);
				preparedStatement.setInt(1,DetailId);
				resultSet = preparedStatement.executeQuery();
				
				if(resultSet.next())//获得postId
				{
					int postId = resultSet.getInt("postId");
					askPostDetailBean.setPostId(postId);
				}
				
				
				preparedStatement = conn.prepareStatement(sql_q_user);
				preparedStatement.setString(1,userId);
				resultSet = preparedStatement.executeQuery();
				
				
				
				if(resultSet.next())//获得userImage
				{
				   String nickName = resultSet.getString("userName");
				   String head = resultSet.getString("userImagePath");
				   user.account = userId;
				   user.head = head;
				   user.nickName = nickName;
				   
				   askPostDetailBean.user = user;
				   
				   
				      
				   preparedStatement = conn.prepareStatement(sql_q_remark);
				   preparedStatement.setInt(1,DetailId);
				   resultSet = preparedStatement.executeQuery();
				   
				   while(resultSet.next())//获得remark
				   {
					   PostDetailBean.Remark r = new PostDetailBean.Remark();
					   String userId_r = resultSet.getString("userId");
					   int  remarkId_r = resultSet.getInt("remarkId");
					   String time_r =  resultSet.getString("time");
					   String date_r =  resultSet.getString("date");
					   String content_r  = resultSet.getString("content");
					   r.content = content_r;
					   r.detailId = DetailId;
					   r.remarkId = remarkId_r;
					   r.time = time_r;
					   r.date = date_r;
					   
					   PostDetailBean.Remark.User user_r = new PostDetailBean.Remark.User();
					   user_r.account = userId_r;
					   r.user =user_r;
					 
					   remarkList.add(r);
				   }
				   
                    int length = remarkList.size();
                    for(int i=0;i<length;i++)//获得remark中的userImage
                    {
                    	String userId_r_u  =  remarkList.get(i).user.account;
     				   preparedStatement = conn.prepareStatement(sql_q_user);
    				   preparedStatement.setString(1,userId_r_u);
    				   resultSet = preparedStatement.executeQuery();				   
    				   if(resultSet.next())
    				   {
    					   String nickName_r_u = resultSet.getString("userName");
    					   String head_r_u = resultSet.getString("userImagePath");
    					   remarkList.get(i).user.head = head_r_u;
    					   remarkList.get(i).user.nickName = nickName_r_u;
    				   }
                    }
                    
                    
                    for(int i=0;i<length;i++)
                    {
                    	int remarkId = remarkList.get(i).remarkId;
                    	LinkedList<String>  remarkPicList = new LinkedList<String>();
                    	remarkList.get(i).bitmapPath = remarkPicList;
                    	preparedStatement = conn.prepareStatement(sql_q_remark_image);
                    	preparedStatement.setInt(1, remarkId);
                    	resultSet = preparedStatement.executeQuery();
                    	while(resultSet.next())
                    	{
                    		String picPath = resultSet.getString("url");
                    		remarkPicList.add(picPath);
                    	}
                    }
                    
				   isSuccess = true;
				}	
			}
			
			preparedStatement.close();
			conn.close();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(isSuccess)
		{
		  askPostDetailBean.setResponseCode(PostDetailBean.POST_DETAIL_DOWN_LOAD_RESPONSE_SUCCESSED);
		}
		else
		{
		  askPostDetailBean.setResponseCode(PostDetailBean.POST_DETAIL_DOWN_LOAD_RESPONSE_FAILED);
		}
		return  askPostDetailBean;
	}
	
	
	public  PostDetailBean deletePostDetailService(int DetailId)
	{
		boolean  isSuccess = false;
		String sql_d_postDetail = "delete from PostDetail where detailId = ?";
		String sql_d_remark  = "delete from Remark where detailId = ?";
		String sql_d_post = "delete from Post where detailId = ?";
		String sql_d_image = "delete from PostDetailPic where detailId = ?";

		PostDetailBean  askPostDetailBean = new PostDetailBean();
		
		try
		{
			Connection conn = JDBCConn.getConnection();
			preparedStatement = conn.prepareStatement(sql_d_post);
			preparedStatement.setInt(1,DetailId);
			preparedStatement.execute();
			preparedStatement = conn.prepareStatement(sql_d_image);
			preparedStatement.setInt(1,DetailId);
			preparedStatement.execute();
			preparedStatement = conn.prepareStatement(sql_d_remark);
			preparedStatement.setInt(1,DetailId);
			preparedStatement.execute();   
			preparedStatement = conn.prepareStatement(sql_d_postDetail);
			preparedStatement.setInt(1,DetailId);
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
		  askPostDetailBean.setResponseCode(PostDetailBean.POST_DETAIL_DELETE_RESPONSE_SUCCESSED);
		}
		else
		{
		  askPostDetailBean.setResponseCode(PostDetailBean.POST_DETAIL_DELETE_RESPONSE_FAILED);
		}
		return  askPostDetailBean;
	}
	

}
