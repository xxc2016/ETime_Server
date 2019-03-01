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

import bean.PostBean;
import service.PostService;
import util.JsonManager;


/**
 * Servlet implementation class PostServlet
 */
@WebServlet("/PostServlet")
public class PostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostServlet() {
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
		PostBean postBean = JsonManager.JsonToPostBean(responseStrBuilder.toString());
		//System.out.println(postBean);
		
		PostBean askPostBean = null;
		//System.out.print("code:"+postBean.getRequestCode());
		PostService service  = new PostService();
		if(postBean.getRequestCode()==PostBean.POST_UP_STORE_REQUEST)
		{
			askPostBean = service.upStorePostService(postBean.getPosts());
		}
		if(postBean.getRequestCode()==PostBean.POST_DOWN_LOAD_COMMUNITY_ALL_REQUEST)
		{
			askPostBean = service.downLoadALLPostService();
		}
		if(postBean.getRequestCode()==PostBean.POST_DOWN_LOAD_LIST_REQUEST)
		{
			askPostBean = service.downLoadPostByListService(postBean.getRequestPostList());
		}
		if(postBean.getRequestCode()==PostBean.POST_COMMUNITY_GET_LIST_REQUEST)
		{
			askPostBean = service.downLoadPostListByLineService(postBean.getRequestPostList());
		}
		
        if(askPostBean!=null)
        {
			String json = JsonManager.PostBeanToJson(askPostBean);
			System.out.println("result:"+json);
			out.print(json);
        }
	}
}
