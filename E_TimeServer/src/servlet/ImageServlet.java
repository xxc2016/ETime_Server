package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import bean.PostDetailBean;
import bean.RemarkBean;
import service.PostDetailService;
import service.RemarkService;
import util.JsonManager;
import util.UUID_generator;
import util.picCompress;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet("/ImageServlet")
@MultipartConfig    //非常重要!!
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter  out = response.getWriter();
		
		String  jsonType = request.getParameter("jsonType");
		System.out.println("jsonType:"+jsonType);
		if(jsonType==null)
		{
			return;
		}
		if(jsonType.equals("postDetailBean"))
		{
			doGet_PostDetailBean(request,response,out);
		}
		if(jsonType.equals("remarkBean"))
		{
		    doGet_RemarkBean(request,response,out);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public String getFileName(String header)
	{
		System.out.println(header);
		String [] tempArr1 = header.split(";");
		String [] tempArr2 = tempArr1[2].split("=");
		String tempArr3 = null;
		for(int i=1;i<tempArr2.length;i++)
		{
			tempArr3 +=tempArr2[i];
		}
		String fileName = tempArr3.substring(tempArr3.lastIndexOf("\\")+1).replace("\"", "");
		return fileName;
	}
	
	protected void doGet_PostDetailBean(HttpServletRequest request, HttpServletResponse response,PrintWriter out) throws ServletException, IOException {
		
		String savePath = request.getServletContext().getRealPath(File.separator+"upload"+File.separator+"postImage");
		File folderPath = new File(savePath);//建立文件夹
		if(!folderPath.exists())
		{
		    folderPath.mkdirs();   
		}
	
		System.out.println("savepath:"+savePath);

		String json = request.getParameter("json");
		

		System.out.println("json:"+json);
		
		int picNumber =Integer.parseInt(request.getParameter("picNumber"));//图片数量
		System.out.println("picNumber:"+picNumber);
		LinkedList<String>  picNames = new LinkedList<String>();
		LinkedList<String>  picNames_src = new LinkedList<String>();
		
		for(int i=0;i<picNumber;i++)
		{
			Part part = request.getPart("file"+i);
			String header = part.getHeader("content-disposition");
			String fileName = getFileName(header);
			String [] fileType = fileName.split("\\.");
			fileName = UUID_generator.get_UUID_no_Line();
			String file_path = savePath+File.separator+fileName+"."+fileType[1];
			part.write(file_path);
			System.out.println(file_path);
			String file_url =  "/upload/postImage/"+ fileName+"."+fileType[1];
			System.out.println(file_url);
			System.out.println("write over");
			
			
			String srcPath = savePath+File.separator+fileName+"."+fileType[1];
			String desPath = savePath+File.separator+fileName+"_compress"+"."+"jpg";//jpg可以压缩
			
			String result =picCompress.compressPicBySize(srcPath, desPath, 100, 0.4);
			String file_url_compress =   "/upload/postImage/"+ fileName+"_compress"+"."+"jpg";
			if(result==null)
			{
				file_url_compress = file_url;
			}
			
			picNames.add(file_url_compress);
			picNames_src.add(file_url);
		}
		

		PostDetailBean postDetailBean = JsonManager.JsonToPostDetailBean(json);
		postDetailBean.setBitmapPath(picNames);
		
		PostDetailBean askPostDetailBean = null;
		
		PostDetailService service = new PostDetailService();
		
		if(postDetailBean.getRequestCode()==PostDetailBean.POST_DETAIL_UP_STORE_REQUEST)
		{
	    	askPostDetailBean = service.upStorePostDetailService(postDetailBean.user.account,
	    			postDetailBean.getTitle(), postDetailBean.getContent(),postDetailBean.getTime(),
	    			postDetailBean.getPic(), postDetailBean.getDate(),picNames,picNames_src);
		}
		if(askPostDetailBean!=null)
		{
			json = JsonManager.PostDetailBeanToJson(askPostDetailBean);
			System.out.println(json);
			out.print(json);
			out.flush();
		}
		out.close();

	}
	
	
	protected void doGet_RemarkBean(HttpServletRequest request, HttpServletResponse response,PrintWriter out) throws ServletException, IOException {
		
		String savePath = request.getServletContext().getRealPath(File.separator+"upload"+File.separator+"remarkImage");
		File folderPath = new File(savePath);//建立文件夹
		if(!folderPath.exists())
		{
		    folderPath.mkdirs();   
		}
	
		System.out.println("savepath:"+savePath);

		String json = request.getParameter("json");
		
		System.out.println("json:"+json);
		
		int picNumber =Integer.parseInt(request.getParameter("picNumber"));//图片数量
		System.out.println("picNumber:"+picNumber);
		LinkedList<String>  picNames = new LinkedList<String>();
		LinkedList<String>  picNames_src = new LinkedList<String>();
		
		for(int i=0;i<picNumber;i++)
		{
			Part part = request.getPart("file"+i);
			String header = part.getHeader("content-disposition");
			String fileName = getFileName(header);
			String [] fileType = fileName.split("\\.");
			fileName = UUID_generator.get_UUID_no_Line();
			String file_path = savePath+File.separator+fileName+"."+fileType[1];
			part.write(file_path);
			System.out.println(file_path);
			String file_url =  "/upload/remarkImage/"+ fileName+"."+fileType[1];
			System.out.println(file_url);
			System.out.println("write over");
			
			
			String srcPath = savePath+File.separator+fileName+"."+fileType[1];
			String desPath = savePath+File.separator+fileName+"_compress"+"."+"jpg";//jpg可以压缩
			
			String result =picCompress.compressPicBySize(srcPath, desPath, 100, 0.4);
			String file_url_compress =   "/upload/remarkImage/"+ fileName+"_compress"+"."+"jpg";
			if(result==null)
			{
				file_url_compress = file_url;
			}
			
			picNames.add(file_url_compress);
			picNames_src.add(file_url);
		}
		

		RemarkBean remarkBean = JsonManager.JsonToRemarkBean(json);
		remarkBean.setBitmapPath(picNames);
		
		RemarkBean askRemarkBean = null;
		
		RemarkService service = new RemarkService();
		
		if(remarkBean.getRequestCode()==RemarkBean.REMARK_UP_STORE_REQUEST)
		{
	    	askRemarkBean = service.upStoreRemarkService(remarkBean.getUser().account, remarkBean.getDetailId(),
	    			remarkBean.getTime(),remarkBean.getContent(), remarkBean.getDate(), picNames,picNames_src);
		}
		if(askRemarkBean!=null)
		{
			json = JsonManager.RemarkBeanToJson(askRemarkBean);
			System.out.println(json);
			out.print(json);
			out.flush();
		}
		out.close();

	}

}
