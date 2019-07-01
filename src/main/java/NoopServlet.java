import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoopServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    int count=0;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().append("OK+"+count++);
    }

}