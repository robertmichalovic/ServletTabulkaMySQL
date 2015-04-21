package databaze.mysql;
import java.sql.*;
public class ConnectMySQLJDBC {
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");	} 
		catch (ClassNotFoundException e) {
			System.out.println("Neporadarilo se nacist driver");
			e.printStackTrace();	}	}
	private Connection pripojeni = null;
	private Statement dotaz = null;
	public Connection getPripojeni() 					{	return pripojeni;	}
	public void setPripojeni(Connection pripojeni) 		{	this.pripojeni = pripojeni;	}
	public Statement getDotaz() 						{	return dotaz;	}
	public void setDotaz(Statement dotaz) 				{	this.dotaz = dotaz;	}
	public ConnectMySQLJDBC(){
		/**	Pripojime se k databazi **/
		System.out.println("Konstruktor pripojeni k databazi");
		try {
			this.setPripojeni(DriverManager.getConnection("jdbc:mysql://localhost/sakila","Java2","asdasdasd"));
			this.setDotaz(pripojeni.createStatement());
			System.out.println("Pripojeni se podarilo a vyrobit dotaz taky ");	} 
		catch (SQLException e) {
			System.out.println("Nepodarilo se pripojit do databaze");
			e.printStackTrace();	}	}
	public void zjistiPocetTabulek(){
		ResultSet rs=null;
		try{
			rs = dotaz.executeQuery("SHOW TABLES");		//	rs = dotaz.executeQuery("SHOW TABLES FROM sakila");
			System.out.println("Podarilo se pripojit k serveru a ziskat data");
			while(rs.next()){
				System.out.println(rs.getString(1));	}	}
		catch(SQLException e){ 
			System.out.println("Nepodarilo se pripojit k databazi");
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
			System.out.println("Zde je zprava :  "+e.getMessage());
			e.printStackTrace();	}	}
	public void odpojeniDatabaze(){
		try {
			dotaz.close();
			pripojeni.close();	} 
		catch (SQLException e) {
			System.out.println("Nepodarilo se odpojit od databaze");
			e.printStackTrace();	}	}
}
