/*	Do prohlizece zadame : http://localhost:8080/Java.Servlet12.RedirectoringMySQL/ nebo http://localhost:8080/Java.Servlet12.RedirectoringMySQL/index.html
 *  nezpomet pridat do projektu connector pro databazi
 */
package servlet3.datab.jdbc;
import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import databaze.mysql.*;
@WebServlet(name = "/ServletDatabTabJDBC",
			urlPatterns = {"/TabulkaServletJDBC","/tabulkaJDBC"})
public class TabulkaMySQL extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ConnectMySQLJDBC datab;
    public void init(){
    	System.out.println("Metoda Servlet Databaze");
    	datab = new ConnectMySQLJDBC();	}
	public void destroy() {
		System.out.println("Destroy sekvence");
		datab.odpojeniDatabaze();
		datab = null;	}
    public TabulkaMySQL() {
        super();	}
    private void zobrazTabulku(PrintWriter out,String jmenoTabulky){
    	out.println("<table width=50% align=center cellpadding=3 cellspacing=2 border=4>");
    	ResultSet rs=null; 
    	ResultSetMetaData rsMeta=null;
    	String dotazString = "SELECT * FROM "+jmenoTabulky;
    	try {
			rs = datab.getDotaz().executeQuery(dotazString);
			rsMeta = rs.getMetaData();		} 
    	catch (SQLException e) {
			System.out.println("Pokusime se ziskat data z MySQL databaze");
			e.printStackTrace();	}
    	int pocetSloupcu;
		try {
			pocetSloupcu = rsMeta.getColumnCount();
			/*Vypiseme hlavicku - nazvy sloupcu */
			out.println("<tr>");
			for(int i=1;i<=pocetSloupcu;i++){
				out.println("<td bgcolor=lightblue>"+rsMeta.getColumnName(i)+"</td>");	}
			out.println("</tr>");
			/*Vypiseme hodnoty*/
			while(rs.next()){
				out.println("<tr>");
				for(int i=1;i<=pocetSloupcu;i++){
					out.println("<td bgcolor=lightgrey>"+rs.getString(i)+"</td>");	}
				out.println("</tr>");	}	} 
		catch (SQLException e) {
			System.out.println("Nepodarilo se vytvorit tabulku");
			e.printStackTrace();	}
    	out.println("</table><BR>");	}
    private void zobrazTabulky(PrintWriter out) {
		ResultSet resultSet = null;
		try{
			resultSet = datab.getDotaz().executeQuery("SHOW TABLES");		//	rs = dotaz.executeQuery("SHOW TABLES FROM sakila");
			System.out.println("Podarilo se pripojit k serveru a ziskat data");		}
		catch (SQLException e) {
			System.out.println("Pokusime se ziskat data z MySQL databaze");
			e.printStackTrace();	}
		String jmenoTab1,jmenoTab2;
		try {
			/**Objekt dotazu v metode bude zmenen a proto je potreba ziskat jmena tabulek drive nez se zmeni a znehodnoti tak resultSet v teto metode**/
			resultSet.next();
			jmenoTab1 = resultSet.getString(1);
			resultSet.next();
			jmenoTab2 = resultSet.getString(1);
			zobrazTabulku(out,jmenoTab1);
			out.println("<BR><BR>Zde vypiseme tabulku - cyklovou<BR>");
			zobrazTabulku(out,jmenoTab2);	} 
		catch (SQLException e) {
			e.printStackTrace();	}	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n");
		out.println("<html>\n<head><title>Tabulka Databaze - Servlet</title></head>\n");
		out.println("<body bgcolor=\"#95DCDF\">\n");
		out.println("Zde vypiseme tabulku - cyklovou<BR>");
		//datab.zjistiPocetTabulek();			//	Funguje
		zobrazTabulky(out);
		out.println("</body></html>");	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	}
}