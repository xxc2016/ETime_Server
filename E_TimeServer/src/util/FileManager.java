package util;

import java.io.File;

public class FileManager {
	
	static public void deleteFile(String filePath)
	{
		try
		{
		  if(filePath==null)
		  {
			  return;
		  }
		  String basePath = System.getProperty("catalina.home")+File.separator+"webapps"+File.separator+"E_TimeServer";
		  String [] subPath = filePath.split("/");
		  for(int i=0;i<subPath.length;i++)
		  {
			  basePath +=File.separator +subPath[i];
		  }
		  System.out.println("delete file:"+basePath);
		  
		  File file = new File(basePath);
		  if(file.exists())
		  {
			  file.delete();
		  }
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
