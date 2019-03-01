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

import bean.UserBean;
import service.UserBeanService;
import util.JsonManager;

/**
 * Servlet implementation class UserBeanServlet
 */
@WebServlet("/UserBeanServlet")
public class UserBeanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserBeanServlet() {
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
		UserBean userBean = JsonManager.JsonToUserBean(responseStrBuilder.toString());
		
		UserBean askUserBean = null;
		UserBeanService service = new UserBeanService();
		if(userBean.getRequestCode()==UserBean.USER_UP_FOLLOWLIST_REQUEST)
		{
			askUserBean =  service.upStoreUserBeanFollowListService(userBean.getAccount(),userBean.getFollowList());
		}
		if(userBean.getRequestCode()==UserBean.USER_DOWN_LOAD_REQUEST)
		{
			askUserBean =  service.downLoadUserBeanService(userBean.getAccount());
		}
		if(userBean.getRequestCode()==UserBean.USER_DELETE_FOLLOWLIST_REQUEST)
		{
			askUserBean =  service.deleteUserBeanFollowListService(userBean.getAccount(),userBean.getFollowList());
		}
		

		
        if(askUserBean!=null)
        {
			String json = JsonManager.UserBeanToJson(askUserBean);
			System.out.println("result:"+json);
			out.print(json);
        }
	}

}
