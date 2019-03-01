package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import bean.ImageBean;
import database.JDBCConn;

public class ImageService {
	
	public PreparedStatement preparedStatement = null;
	public Statement statement = null;
	public ResultSet  resultSet = null;
	
	public ImageBean  getSourceSerivce(List<String> list_compress,List<Integer> list_position)
	{
		 boolean isSuccess = false;
		 String sql_q = "select * from picSrcList where compressPic = ?";
		 ImageBean askImageBean =new ImageBean();
		 LinkedList<String> list_src = new LinkedList<String>();
		 askImageBean.setImagePath_src(list_src);
		 askImageBean.setImagePath(list_compress);
		 
		 askImageBean.setImagePosition(list_position);	 
		 try
		 {
			    if(list_compress !=null)
			    {		    
					Connection conn = JDBCConn.getConnection();
					for(int i=0;i<list_compress.size();i++)
					{
						String url_c = list_compress.get(i);
						String url_s = null;
						preparedStatement = conn.prepareStatement(sql_q);
						preparedStatement.setString(1, url_c);
						resultSet =  preparedStatement.executeQuery();
						if(resultSet.next())
						{
						   url_s = resultSet.getString("srcPic");
						}
						list_src.add(url_s);
					}
					
					if(preparedStatement!=null)
					{
					  preparedStatement.close();
					}
					conn.close();
					isSuccess = true;
			    }
				
		 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 
		 
		 if(isSuccess)
		 {
			 askImageBean.setResponseCode(ImageBean.IMAGE_GET_SOURCE_RESPONSE_SUCCESSED);
		 }
		 else
		 {
			 askImageBean.setResponseCode(ImageBean.IMAGE_GET_SOURCE_RESPONSE_FAILED);
		 }
		 
		 return askImageBean;
    }
		 
}
