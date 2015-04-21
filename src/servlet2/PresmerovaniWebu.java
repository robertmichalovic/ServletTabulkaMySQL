package servlet2;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
@WebServlet( 	name = "/PresmerovaniWebu",
				urlPatterns = {"/presmeruj","/sendNewWeb"})
public class PresmerovaniWebu extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public PresmerovaniWebu() {
        super();	}
	@SuppressWarnings("static-access")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String site = new String("http://www.ihned.cz");
		response.setStatus(response.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", site); 	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	}
}
