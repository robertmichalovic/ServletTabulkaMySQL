/*	Do prohlizece zadame : http://localhost:8080/Java.Servlet12.RedirectoringMySQL/ nebo http://localhost:8080/Java.Servlet12.RedirectoringMySQL/index.html
 * 
 * 
 */
package servlet1;
import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
@WebServlet(name = "/ServletX",
			urlPatterns = {"/sendX","/routingX"},
			initParams = {	@WebInitParam(name = "titulek", value = "Nadpis Webove stranky") })
public class ServletX extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletConfig c;
    public void init( ){		
    	c = this.getServletConfig();
    	System.out.println("Metoda Init 2");		}
    public ServletX() {
        super();	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Metoda GET");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n");
		//out.println("<html>\n<head><title>" + c.getInitParameter("titulek") + "</title></head>\n");
		out.println("<html>\n<head><title>");
		out.println(c.getInitParameter("titulek"));
		out.println("</title></head>\n");
		out.println("<body bgcolor=\"#27CAKD\">\n");
		out.println("Muj TEXT O NICEM<BR><BR>");
		out.println("<a href=\"http://localhost:8080/Java.Servlet12.RedirectoringMySQL/presmeruj\" target=\"_blank\"> Pøesmìrovaní na servlet2</a> - na defaultni servlet<BR>");
		out.println("<form action=\"presmeruj\" method=\"GET\" target=\"_blank\"><input type=\"submit\" value=\"Spusti Servlet 2\" /></form><BR>");
		out.println("<a href=\"http://localhost:8080/Java.Servlet12.RedirectoringMySQL/\" target=\"_blank\"> Zpet na hlavni stranku</a> Obycejny index.html<BR>");
		out.println("</body></html>");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Metoda POST");	}
}