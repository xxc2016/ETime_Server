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

import bean.RemarkBean;
import service.RemarkService;
import util.JsonManager;

/**
 * Servlet implementation class RemarkServlet
 */
@WebServlet("/RemarkServlet")
public class RemarkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemarkServlet() {
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
		RemarkBean remarkBean = JsonManager.JsonToRemarkBean(responseStrBuilder.toString());
		
		RemarkBean askRemarkBean = null;
		RemarkService service = new RemarkService();
		if(remarkBean.getRequestCode()==RemarkBean.REMARK_UP_STORE_REQUEST)
		{
			askRemarkBean =  service.upStoreRemarkService(remarkBean.getUser().account,remarkBean.getDetailId()
					,remarkBean.getTime(),remarkBean.getContent(),remarkBean.date,remarkBean.getBitmapPath(),remarkBean.getBitmapPath());
		}
		if(remarkBean.getRequestCode()== RemarkBean.REMARK_DOWN_LOAD_REQUEST)
		{
			askRemarkBean = service.downLoadRemarkService(remarkBean.getRemarkId());
		}
		if(remarkBean.getRequestCode()== RemarkBean.REMARK_DELETE_REQUEST)
		{
			askRemarkBean = service.deleteRemarkService(remarkBean.getRemarkId());
		}
		
		
        if(askRemarkBean!=null)
        {
			String json = JsonManager.RemarkBeanToJson(askRemarkBean);
			System.out.println("result:"+json);
			out.print(json);
        }
	}

}
