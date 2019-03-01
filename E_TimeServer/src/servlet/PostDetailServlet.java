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

import bean.PostDetailBean;
import service.PostDetailService;
import util.JsonManager;

/**
 * Servlet implementation class PostDetailServlet
 */
@WebServlet("/PostDetailServlet")
public class PostDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostDetailServlet() {
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
		PostDetailBean postDetailBean = JsonManager.JsonToPostDetailBean(responseStrBuilder.toString());
		
		PostDetailBean askPostDetailBean = null;
		PostDetailService service = new PostDetailService();
		if(postDetailBean.getRequestCode()==PostDetailBean.POST_DETAIL_DOWN_LOAD_REQUEST)
		{
			askPostDetailBean = service.downLoadPostDetailService(postDetailBean.getDetailId());
		}
		if(postDetailBean.getRequestCode()==PostDetailBean.POST_DETAIL_DELETE_REQUEST)
		{
			askPostDetailBean = service.deletePostDetailService(postDetailBean.getDetailId());
		}
		if(postDetailBean.getRequestCode()==PostDetailBean.POST_DETAIL_UP_STORE_REQUEST)
		{
	    	askPostDetailBean = service.upStorePostDetailService(postDetailBean.user.account,
	    			postDetailBean.getTitle(), postDetailBean.getContent(),postDetailBean.getTime(),
	    			postDetailBean.getPic(), postDetailBean.getDate(),postDetailBean.getBitmapPath(),postDetailBean.getBitmapPath());
		}
		
        if(askPostDetailBean!=null)
        {
			String json = JsonManager.PostDetailBeanToJson(askPostDetailBean);
			System.out.println("result:"+json);
			out.print(json);
        }
	}

}
