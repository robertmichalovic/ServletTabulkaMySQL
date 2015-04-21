/*	Do prohlizece zadame : http://localhost:8080/Java.Servlet12.RedirectoringMySQL/ nebo http://localhost:8080/Java.Servlet12.RedirectoringMySQL/index.html
 *  nezpomet pridat do projektu connector pro databazi
 */
package servlet3.datab.datasource;
import java.io.*;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import databaze.mysql.ConnectMySQLDataSource;
@WebServlet(name = "/ServletDatabTab",
			urlPatterns = {"/TabulkaServlet","/tabulka"})
public class TabulkaMySQL extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ConnectMySQLDataSource datab;
    public void init(){
    	//System.out.println("Metoda Servlet Databaze");
    	datab = new ConnectMySQLDataSource();	}
	public void destroy() {
		//System.out.println("Destroy sekvence");
		datab.odpojeniDatabaze();
		datab = null;	}
    public TabulkaMySQL() {
        super();	}
	@SuppressWarnings("unused")
	private void vypisTabulkuKonzole() {				//	testovaci metoda pro konzolovy vypis tabulky
    	String dotazString = "SELECT id,vek,krestni,prijmeni FROM tabtest";
    	ResultSet rs=null; ResultSetMetaData rsMeta;
    	int pocetRadku,pocetSloupcu,id,age;
    	try {
			rs = datab.getDotaz().executeQuery(dotazString);	} 
    	catch (SQLException e) {
			System.out.println("Pokusime se ziskat data z MySQL databaze");
			e.printStackTrace();	}
    	String first,last;
		try {
			rsMeta = rs.getMetaData();
			pocetRadku = rs.getRow(); 
			pocetSloupcu = rsMeta.getColumnCount();
			System.out.println("Pocet radku : "+pocetRadku+"\tPocet sloupcu :"+pocetSloupcu);
			/*Vypiseme hlavicku - nazvy sloupcu */
			for(int i=1;i<=pocetSloupcu;i++){
				System.out.print(rsMeta.getColumnName(i)+"\t");	}
			/*Vypiseme hodnoty*/
			while(rs.next()){
	            id  = rs.getInt("id");
	            age = rs.getInt("vek");
	            first = rs.getString("krestni");
	            last = rs.getString("prijmeni");
	            System.out.println("Objekt :"+id+"\t"+age+"\t"+first+"\t"+last);	}	} 
		catch (SQLException e) {
			System.out.println("Nepodarilo se vytvorit tabulku");
			e.printStackTrace();	}	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//datab.vypisemeVerziDatabaze();			//	Funguje
		//datab.vypisemeTabulku();					//	Funguje
		//vypisTabulkuKonzole();					//	Funguje - nutne static
		//System.out.println("Metoda GET");			// Funguje
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n");
		out.println("<html>\n<head><title>Tabulka Databaze - Servlet</title></head>\n");
		out.println("<body bgcolor=\"#21AAFF\">\n");
		out.println("Zde vypiseme tabulku - cyklovou");
		/**		<tr></tr> ... radek, <td><td> .. sloupec	****/
		out.println("<table width=50% align=center cellpadding=3 cellspacing=2 border=4>");		
		String dotazString = "SELECT id,vek,krestni,prijmeni FROM tabtest";
		ResultSet rs=null; ResultSetMetaData rsMeta=null;
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
				out.println("<td bgcolor=lightgrey>"+rs.getInt("id")+"</td>");
				out.println("<td bgcolor=lightgrey>"+rs.getInt("vek")+"</td>");
				out.println("<td bgcolor=lightgrey>"+rs.getString("krestni")+"</td>");
				out.println("<td bgcolor=lightgrey>"+rs.getString("prijmeni")+"</td>");
				out.println("</tr>");	}	} 
		catch (SQLException e) {
			System.out.println("Nepodarilo se vytvorit tabulku");
			e.printStackTrace();	}
		out.println("</table><BR><BR>");
		out.println("</body></html>");	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	}
}
