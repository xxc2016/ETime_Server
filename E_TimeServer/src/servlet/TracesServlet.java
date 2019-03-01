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


import bean.TraceBean;
import service.TracesService;
import util.JsonManager;

/**
 * Servlet implementation class TracesService
 */
@WebServlet("/TracesServlet")
public class TracesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TracesServlet() {
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
		TraceBean traceBean = JsonManager.JsonToTraceBean(responseStrBuilder.toString());
		System.out.println(traceBean);
		
		TraceBean  askTraceBean = null;
		TracesService service =new TracesService();
		
		if(traceBean.getRequestCode()==TraceBean.UP_STORE_REQUEST)
		{
			askTraceBean = service.upStoreTraceService(traceBean.getUserAccount(), traceBean.getTraces());
			System.out.println("regiser result:"+askTraceBean.getResponseCode());
		}
		if(traceBean.getRequestCode() == TraceBean.DOWN_LOAD_REQUEST)
		{
			askTraceBean = service.downLoadTraceService(traceBean.getUserAccount(),traceBean.getTraces());
			System.out.println("login result:"+ askTraceBean.getResponseCode());
		}

			
        if(askTraceBean!=null)
        {
			String json = JsonManager.TraceBeanToJson(askTraceBean);
			System.out.println("result:"+json);
			out.print(json);
        }
	}

}
