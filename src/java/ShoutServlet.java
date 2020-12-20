
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Learn new here
 */
@WebServlet(urlPatterns = "/shoutServlet", asyncSupported = true)
public class ShoutServlet extends HttpServlet{
    public ArrayList<AsyncContext> context = new ArrayList<AsyncContext>();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        AsyncContext newContext = request.startAsync(request, response);
        newContext.setTimeout(10 * 60 * 1000);
        context.add(newContext);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        
        ArrayList<AsyncContext> asyncContexts = new ArrayList<AsyncContext>(context);
            context.clear();
        String htmlMessage = "<p><b>" + request.getParameter("name") + "</b><br/>" + request.getParameter("message") + "</p>";
            for (AsyncContext asyncContext : asyncContexts) {
                try
                {
                    PrintWriter writer = asyncContext.getResponse().getWriter();
                    writer.println(htmlMessage);
                    writer.flush();
                    asyncContext.complete();
                } catch (Exception ex) {
                }
            }
    }
}
