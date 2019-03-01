package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import bean.User;
import service.UserImageService;
import util.JsonManager;
import util.UUID_generator;
import util.picCompress;

/**
 * Servlet implementation class StoreImageServlet
 */
@WebServlet("/StoreImageServlet")
@MultipartConfig    //非常重要!!
public class StoreImageServlet extends HttpServlet {//带文件和参数的表单提交
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StoreImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Get");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter  out = response.getWriter();
		
		String savePath = request.getServletContext().getRealPath(File.separator+"upload"+File.separator+"userImage");
		File folderPath = new File(savePath);
		if(!folderPath.exists())
		{
		    folderPath.mkdirs();   
		}
		//String savePath = "C:\\Users\\ThinkPad\\Desktop\\javaWeb\\E_TimeServer\\WebContent\\WEB-INF\\upload\\userImage";
		System.out.println("savepath:"+savePath);
		//String userName = request.getParameter("userName");
		String userAccount = request.getParameter("userAccount");
		
		//System.out.println("userName:"+userName);
		System.out.println("userAccount:"+userAccount);
		Part part = request.getPart("file");
		String header = part.getHeader("content-disposition");
		
		
		String fileName = getFileName(header);
		System.out.print("fileName:"+fileName);
		String [] fileType = fileName.split("\\.");
		fileName = UUID_generator.get_UUID_no_Line();
		String file_path = savePath+File.separator+fileName+"."+fileType[1];
		part.write(file_path);
		System.out.println(file_path);
		String file_url =  "/upload/userImage/"+ fileName+"."+fileType[1];
		System.out.println(file_url);

		System.out.println("write over");
		
		String srcPath = savePath+File.separator+fileName+"."+fileType[1];
		String desPath = savePath+File.separator+fileName+"_compress"+"."+"jpg";//jpg可以压缩
		
		String result =picCompress.compressPicBySize(srcPath, desPath, 100, 0.25);
		String file_url_compress =   "/upload/userImage/"+ fileName+"_compress"+"."+"jpg";
		if(result==null)
		{
			file_url_compress = file_url;
		}
		
		User askUser = null;
		UserImageService service = new UserImageService();
		askUser = service.imageStoreService(userAccount, file_url_compress,file_url);
		
		if(askUser!=null)
		{
			String json = JsonManager.UserToJson(askUser);
			System.out.println(json);
			out.print(json);
			out.flush();
		}
		out.close();	
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

}
