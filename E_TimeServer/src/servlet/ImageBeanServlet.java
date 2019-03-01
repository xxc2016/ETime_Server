package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ImageBean;
import service.ImageService;
import util.JsonManager;

/**
 * Servlet implementation class ImageBeanServlet
 */
@WebServlet("/ImageBeanServlet")
public class ImageBeanServlet extends HttpServlet {//用于接受ImageBean的请求
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageBeanServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter  out = response.getWriter();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(ServletInputStream)request.getInputStream(),"utf-8"));
		StringBuilder  responseStrBuilder = new StringBuilder();
		String line;
		while((line=br.readLine())!=null)
		{
			responseStrBuilder.append(line);
		}
		System.out.println(responseStrBuilder);
		ImageBean imageBean = JsonManager.JsonToImageBean(responseStrBuilder.toString());
		
		ImageBean askImageBean = null;
		ImageService service = new ImageService();
		
		System.out.println("out");
		
		if(imageBean.getRequestCode()== ImageBean.IMAGE_GET_SOURCE_REQUEST)
		{
			System.out.println("into");
			askImageBean = service.getSourceSerivce(imageBean.getImagePath(),imageBean.getImagePosition());
		}
		
		
		
        if(askImageBean!=null)
        {
			String json = JsonManager.ImageBeanToJson(askImageBean);
			System.out.println("result:"+json);
			out.print(json);
        }else
        {
        	System.out.print("result is null");
        }
		
	}

}
