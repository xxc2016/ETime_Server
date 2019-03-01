package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import bean.PostBean;
import database.JDBCConn;

public class PostService {
	
	public PreparedStatement preparedStatement = null;
	public ResultSet  resultSet = null;
	
	public   PostBean upStorePostService(List<PostBean.Post> list)//留接口  没实现
	{
		boolean  isSuccess = false;
		if(list!=null)
		{
			
		}

		
		PostBean  askPostBean = new PostBean();
		if(isSuccess)
		{
		  askPostBean.setResponseCode(PostBean.POST_UP_STORE_RESPONSE_SUCCESSED);
		}
		else
		{
		  askPostBean.setResponseCode(PostBean.POST_UP_STORE_RESPONSE_FAILED);
		}
		return  askPostBean;
	}
	
	public   PostBean downLoadALLPostService()//下载所有的帖子预览类
	{
		boolean  isSuccess = false;
		String sql_q = "select * from Post order by postId desc";
		String sql_q_postDetail = "select * from PostDetail where detailId = ?";
		String sql_q_user = "select * from userImage where userId = ?";
		
		PostBean askPostBean = new PostBean();
		LinkedList<PostBean.Post>  posts  =new LinkedList<PostBean.Post>(); 
		askPostBean.setPosts(posts);
		
		try
		{
			Connection conn = JDBCConn.getConnection();
			preparedStatement = conn.prepareStatement(sql_q);
			resultSet = preparedStatement.executeQuery();
			
			 
			
			while(resultSet.next())//获得post
			{
				PostBean.Post post  =new PostBean.Post();
				int postId = resultSet.getInt("postId");
				int detailId = resultSet.getInt("detailId");
				int watch =  resultSet.getInt("watch");
				int remark = resultSet.getInt("remark");
				post.PostId = postId;
				post.watch =  watch;
				post.remark = remark;
				post.detailId = detailId;
				
				posts.add(post);
			}
			
			for(int i=0;i<posts.size();i++)//获得postDetail
			{
				PostBean.Post post_d =  posts.get(i);
			    int detailId = post_d.detailId;
			    preparedStatement = conn.prepareStatement(sql_q_postDetail);
			    preparedStatement.setInt(1, detailId);
			    resultSet = preparedStatement.executeQuery();
			    PostBean.Post.User user = new PostBean.Post.User();
			    if(resultSet.next())
			    {
			    	String userId  = resultSet.getString("userId");
			    	String title = resultSet.getString("title");
			    	String pic = resultSet.getString("pic");
			    	String time = resultSet.getString("time");
			    	String date = resultSet.getString("date");
			    	String content = resultSet.getString("content");
			    	
			    	post_d.title = title;
			    	post_d.pic = pic;
			    	post_d.time = time;
			    	post_d.date = date;
			    	
			    	user.account  = userId;
			    	post_d.user = user;
			    }
			}
			
			
			for(int i=0;i<posts.size();i++)//获得user信息
			{
				PostBean.Post.User user = posts.get(i).user;
				String userId = user.account;
				preparedStatement = conn.prepareStatement(sql_q_user);
				preparedStatement.setString(1, userId);
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next())
				{
					String head = resultSet.getString("userImagePath");
					String nickName = resultSet.getString("userName");
					user.head = head;
					user.nickName = nickName;
				}
			}
			
			
			isSuccess =true;
			
			preparedStatement.close();
			conn.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		

		if(isSuccess)
		{
		  askPostBean.setResponseCode(PostBean.POST_DOWN_LOAD_COMMUNITY_ALL_RESPONSE_SUCCESSED);
		}
		else
		{
		  askPostBean.setResponseCode(PostBean.POST_DOWN_LOAD_COMMUNITY_ALL_RESPONSE_FAILED);
		}
		return  askPostBean;
	}
	
	
	
	
	public   PostBean downLoadPostByListService(List<Integer> requestList)
	{
		boolean  isSuccess = false;
		String sql_q = "select * from Post where postId  = ?";
		String sql_q_detail  ="select * from PostDetail where DetailId = ?";
		String sql_q_user = "select * from userImage where userId = ?";
		
		
		PostBean  askPostBean = new PostBean();
		LinkedList<PostBean.Post>  posts = new LinkedList<PostBean.Post>();
		askPostBean.setPosts(posts);
		
		if(requestList != null)
		{
			try
			{
				Connection conn = JDBCConn.getConnection();
				for(int i=0;i<requestList.size();i++)
				{
					int postId = requestList.get(i);
					preparedStatement = conn.prepareStatement(sql_q);
					preparedStatement.setInt(1, postId);
					resultSet = preparedStatement.executeQuery();
					
					if(resultSet.next())//获得post
					{
						PostBean.Post post = new PostBean.Post();
						PostBean.Post.User user = new PostBean.Post.User();
						post.user = user;
						posts.add(post);
						
						int detailId = resultSet.getInt("detailId");
						int watch =  resultSet.getInt("watch");
						int remark = resultSet.getInt("remark");
						post.PostId = postId;
						post.watch =  watch;
						post.remark = remark;
						post.detailId = detailId;
						
						preparedStatement = conn.prepareStatement(sql_q_detail);
						preparedStatement.setInt(1, detailId);
						resultSet = preparedStatement.executeQuery();
						if(resultSet.next())//获得postDetail
						{
					    	String userId  = resultSet.getString("userId");
					    	String title = resultSet.getString("title");
					    	String pic = resultSet.getString("pic");
					    	String time = resultSet.getString("time");
					    	String date = resultSet.getString("date");
					    	String content = resultSet.getString("content");
					    	
					    	post.title = title;
					    	post.pic = pic;
					    	post.time = time;
					    	post.date = date;
					    	
					    	post.user.account = userId;
					    	
							preparedStatement = conn.prepareStatement(sql_q_user);
							preparedStatement.setString(1, userId);
							resultSet = preparedStatement.executeQuery();
							
							if(resultSet.next())//获得userImage
							{
								String head = resultSet.getString("userImagePath");
								String nickName = resultSet.getString("userName");
								post.user.head = head;
								post.user.nickName = nickName;
							}
				
						}
					}
					
				}
				   isSuccess = true;
				   preparedStatement.close();
				   conn.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
	
		if(isSuccess)
		{
		  askPostBean.setResponseCode(PostBean.POST_DOWN_LOAD_LIST_RESPONSE_SUCCESSED);
		}
		else
		{
		  askPostBean.setResponseCode(PostBean.POST_DOWN_LOAD_LIST_RESPONSE_FAILED);
		}
		return  askPostBean;
	}
	
	
	public   PostBean downLoadPostListByLineService(List<Integer> requestList)
	{
		boolean  isSuccess = false;
		String sql_q = "select * from Post where postId < ? order by postId desc";
		String sql_q_top = "select * from Post order by postId desc";
		PostBean  askPostBean = new PostBean();
		
		if(requestList!=null && requestList.size()>=2)
		{
			try
			{
			Connection conn = JDBCConn.getConnection();
			LinkedList<Integer> resultList = new LinkedList<Integer>();
			askPostBean.setResponsePostList(resultList);
			int postLine = requestList.get(0);
			int postNumber = requestList.get(1);
			int count = 0;
			
			if(postLine == PostBean.POST_DOWN_LOAD_LIST_TOP)
			{
				preparedStatement = conn.prepareStatement(sql_q_top);

			}else
			{
				preparedStatement = conn.prepareStatement(sql_q);
				preparedStatement.setInt(1,postLine);
			}
			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				if(count==postNumber) {
					break;
				}
				count++;
				int postId = resultSet.getInt("postId");
				resultList.add(postId);
			}
			
			isSuccess = true;
			preparedStatement.close();
			conn.close();
			
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			
		}

		if(isSuccess)
		{
		  askPostBean.setResponseCode(PostBean.POST_COMMUNITY_GET_LIST_RESPONSE_SUCCESSED);
		}
		else
		{
		  askPostBean.setResponseCode(PostBean.POST_COMMUNITY_GET_LIST_RESPONSE_FAILED);
		}
		return  askPostBean;
	
	}
	
}
