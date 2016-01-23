import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import redis.clients.jedis.Jedis;


public class SimpleTextHandlerServlet extends HttpServlet
{
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	ServletContext ctx = request.getServletContext();
    	Jedis jedis = (Jedis) ctx.getAttribute("jedis");
    	String text = request.getParameter("text");
    	String ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
    	if (ipAddress == null) {
    	    ipAddress = request.getRemoteAddr();
    	}
    	if(jedis != null)
	        	jedis.set(ipAddress, text);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	ServletContext ctx = request.getServletContext();
    	Jedis jedis = (Jedis) ctx.getAttribute("jedis");
    	String ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
    	if (ipAddress == null) {
    	    ipAddress = request.getRemoteAddr();
    	}
    	String text = null;
    	text = jedis.get(ipAddress);
    	JSONObject jsonData = null;
		try {
			jsonData = new JSONObject("{'text':"+text+"}");
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonData.toString());
		response.setStatus(HttpServletResponse.SC_OK);	
    }
}
