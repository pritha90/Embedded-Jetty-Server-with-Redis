import java.io.File;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import redis.clients.jedis.Jedis;

public class JettyStart {
    public static void main(String[] args)
            throws Exception
        {
	            Server server = new Server();
	            Jedis jedis = new Jedis("localhost",6379);
	            
			    ServerConnector sconnector = new ServerConnector(server);
	            sconnector.setPort(8080);
	            
	            
		        // register the connector
				server.setConnectors(new Connector[] {sconnector});
			
				//Create servlets to handle GET/POST
	            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
	            context.setContextPath("/");
	            server.setHandler(context);
	     
	            context.addServlet(new ServletHolder(new SimpleTextHandlerServlet()),"/simpletext");
	            context.setAttribute("jedis", jedis);
	            server.start();
	            server.join();
        }      
}
 