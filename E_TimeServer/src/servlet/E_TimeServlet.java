package servlet;

import java.io.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;


import bean.User;
import service.UserImageService;
import service.UserService;
import util.JsonManager;

/**
 * Servlet implementation class E_TimeServlet
 */
@WebServlet("/E_TimeServlet")
public class E_TimeServlet extends HttpServlet {//用户登陆注册和其他服务
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public E_TimeServlet() {
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
		User user = JsonManager.JsonToUser(responseStrBuilder.toString());
		System.out.println(user);
		
		
		User  askUser = null;
		UserService userService = new UserService();
		UserImageService userImageService = new UserImageService();
		
		if(user.getRequestCode()==User.REGISTER_REQUEST)
		{
			askUser = userService.registerService(user.getName(), user.getPassword());
			System.out.println("regiser result:"+askUser.getResponseCode());
		}
		if(user.getRequestCode() ==User.LOGIN_REQUEST)
		{
			askUser = userService.loginService(user.getName(), user.getPassword());
			System.out.println("login result:"+ askUser.getResponseCode());
		}
		if(user.getRequestCode() ==User.IMAGE_DOWNLOAD_REQUEST) {
			askUser = userImageService.imageDownLoadService(user.getName());
			System.out.println("image download result:"+askUser.getResponseCode());
		}
		if(user.getRequestCode() ==User.IMAGE_STORE_EXTRA_REQUEST)
		{
			//System.out.println(user.getName()+user.getNickName());
			askUser = userImageService.imageStoreExtraService(user.getName(),user.getNickName());
			System.out.println("image store extra result:"+askUser.getResponseCode());
		}
			
        if(askUser!=null)
        {
			String json = JsonManager.UserToJson(askUser);
			System.out.println("result:"+json);
			out.print(json);
        }
        
	}

}
