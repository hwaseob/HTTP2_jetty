import org.eclipse.jetty.http2.server.HTTP2CServerConnectionFactory;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class Http2Cleartext {
    public static void main(String[] args) throws Exception {
        // The Jetty Server.
        Server server = new Server();

        ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
//        context.addServlet(new ServletHolder(new Servlet()), "/");
        context.addServlet(NoopServlet.class, "/");
        server.setHandler(context);

        // Common HTTP configuration.
        HttpConfiguration config = new HttpConfiguration();

        // HTTP/1.1 support.
        HttpConnectionFactory http1 = new HttpConnectionFactory(config);

        // HTTP/2 cleartext support.
        HTTP2CServerConnectionFactory http2c = new HTTP2CServerConnectionFactory(config);

        ServerConnector connector = new ServerConnector(server, http1, http2c);
        connector.setPort(8080);
        server.addConnector(connector);

        // Here configure contexts / servlets / etc.

        server.start();
    }
}
