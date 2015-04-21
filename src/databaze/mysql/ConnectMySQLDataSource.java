/*	pridani JDBC - DataSource varianta pro TomCAT - http://tomcat.apache.org/tomcat-8.0-doc/jndi-datasource-examples-howto.html
- nastavime MySQL databazi - pridame uzivatel,databazi apod..
- Eclipse - Projects Explorer - Servers - Tomcat8-config - context.xml ( a pridame tento popis a upravime
	<!-- Configurace datasource - tvz. aplikaèni server pristupuje k databazi    -->
	<Resource name="jdbc/TestDB" auth="Container" type="javax.sql.DataSource"
               maxTotal="100" maxIdle="30" maxWaitMillis="10000"
               username="Java2" password="asdasdasd" driverClassName="com.mysql.jdbc.Driver"
               url="jdbc:mysql://localhost:3306/test"/>
- do web.xml vložime 
  <resource-ref>
      <description>DB Connection</description>
      <res-ref-name>jdbc/TestDB</res-ref-name>
      <res-type>javax.sql.DataSource</res-type>
      <res-auth>Container</res-auth>
  </resource-ref>
- do WebContet - WEB-INF - lib - nakopirujeme connector

Pozn. nezapomet a overit jestli existuje tabulka TabTest ve schematu test
 */
package databaze.mysql;
import java.sql.*;

import javax.naming.*;
import javax.sql.DataSource;
public class ConnectMySQLDataSource {
	private Context ctx;
	private DataSource dataSource;
	private Connection pripojeni = null;
	private Statement dotaz = null;
	public Connection getPripojeni() 					{	return pripojeni;	}
	public void setPripojeni(Connection pripojeni) 		{	this.pripojeni = pripojeni;	}
	public Statement getDotaz() 						{	return dotaz;	}
	public void setDotaz(Statement dotaz) 				{	this.dotaz = dotaz;	}
	public ConnectMySQLDataSource(){
		/**	Pripojime se k databazi **/
		//System.out.println("Konstruktor tridy :");
		try {
			this.ctx = (Context) new InitialContext().lookup("java:comp/env");
			if(ctx == null) System.out.println("Context NULL - KONTROLA");
			this.dataSource = (DataSource) ctx.lookup("jdbc/TestDB");			//	DataSource pristup bez anotace
			this.setPripojeni(dataSource.getConnection());						//	pripojime se na databazi
			this.setDotaz(pripojeni.createStatement());
			System.out.println("Pripojeni se podarilo a vyrobit dotaz taky ");	} 
		catch (SQLException e) {
			System.out.println("Nepodarilo se pripojit do databaze");
			e.printStackTrace();	} 
		catch (NamingException e) {
			System.out.println("Problem s initKontextem nebo DataSource");
			e.printStackTrace();	}	}
	public void vypisemeVerziDatabaze(){	/**Testovaci metoda pro vypis verze databaze**/
		ResultSet rs = null;
        try {
   			rs = dotaz.executeQuery("SELECT VERSION()");
			if (rs.next()) {
	                System.out.println("Verze MySQL Serveru :\t"+rs.getString(1));	}	} 
        catch (SQLException e) {
			System.out.println("ErroCode :"+e.getErrorCode());
			System.out.println("Message :"+e.getMessage());	}	}
	public void vypisemeTabulku(){		/**Testovaci metoda pro vypis tabulky v databazi**/
		ResultSet rs = null;
		 try {
			 rs = dotaz.executeQuery("SELECT id,vek,krestni,prijmeni FROM tabtest");
			 while(rs.next()){
				 int id  = rs.getInt("id");
				 int age = rs.getInt("vek");
				 String first = rs.getString("krestni");
				 String last = rs.getString("prijmeni");
				 System.out.println("Objekt :"+id+"\t"+age+"\t"+first+"\t"+last);	}	} 
	        catch (SQLException e) {
				System.out.println("ErroCode :"+e.getErrorCode());
				System.out.println("Message :"+e.getMessage());	}	}
	public void odpojeniDatabaze(){
		try {
			dotaz.close();
			pripojeni.close();
			dataSource = null;
			ctx = null;	} 
		catch (SQLException e) {
			System.out.println("Nepodarilo se odpojit od databaze");
			e.printStackTrace();	}	}
}
