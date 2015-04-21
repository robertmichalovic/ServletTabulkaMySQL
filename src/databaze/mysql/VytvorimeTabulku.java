/*	Nutne pripojit driver(connector) - vytvorit datou databazi (test) a uzivatele s heslem		*/
package databaze.mysql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class VytvorimeTabulku {
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");	} 
		catch (ClassNotFoundException e) {
			System.out.println("Neporadarilo se nacist driver");
			e.printStackTrace();	}	}
	public static void main(String[] args) {
		System.out.println("Start Programu");
		Connection pripojeni = null;
		Statement dotaz = null;
		/**	Pripojime se k databazi **/
		try {
			pripojeni = DriverManager.getConnection("jdbc:mysql://localhost/test","Java2","asdasdasd");
			dotaz = pripojeni.createStatement();
			System.out.println("Pripojeni se podarilo a vyrobit dotaz taky ");	} 
		catch (SQLException e) {
			System.out.println("Nepodarilo se pripojit do databaze");
			e.printStackTrace();	}
		/**	Vytvorime tabulku s  **/
		try {
			System.out.println("Dotaz se podarilo vytvorit, Pokusime se vytvorit tabulku ");
			dotaz.executeUpdate("create table TabTest (id INT,vek INT,krestni VARCHAR(20),prijmeni VARCHAR(20))");
			System.out.println("Tabulka se podaøila vytvoøit ");
			System.out.println("Pokusime se provest zapis do databaze");
			dotaz.executeUpdate("INSERT INTO TabTest(id,vek,krestni,prijmeni) VALUES (100,18,'Zara','Ali')");
			dotaz.executeUpdate("INSERT INTO TabTest(id,vek,krestni,prijmeni) VALUES (101,25,'Mahnaz','Fatma')");
			dotaz.executeUpdate("INSERT INTO TabTest(id,vek,krestni,prijmeni) VALUES (102,30,'Zaid','Khan')");
			dotaz.executeUpdate("INSERT INTO TabTest(id,vek,krestni,prijmeni) VALUES (103,28,'Sumit','Mittal')");
			System.out.println("Podarilo se provest ulozeni");	} 
		catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
			System.out.println("Zde je zprava :  "+e.getMessage());
			e.printStackTrace();	}
		try {
			System.out.println("Ukoncime relace pripojeni");
			dotaz.close();
			pripojeni.close();	} 
		catch (SQLException e) {
			System.out.println("Nepodarilo se provest odpojeni");
			e.printStackTrace();	}
		System.out.println("Konec Programu");	}
}
