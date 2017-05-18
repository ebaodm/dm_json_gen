package json_call_API;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBC_connect {
	/**
     * JDBC Connection
     * @param URL, USER, PWD
     * @return connection
     * @author  copy on internet 
     * @Date   5/9/2016 5:31:36 PM
     * @Date modification
     */

	public Connection JDBCConnection(String URL, String USER ,String PWD) {
		System.out.println("-------- Oracle JDBC Connection Testing ------");
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
		} catch (ClassNotFoundException e) {

			System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
			System.exit(1);

		}
        
		System.out.println("Oracle JDBC Driver Registered!");
		
		/** Attempts to establish a connection **/
		Connection connection = null;
		try {

			connection = DriverManager.getConnection(URL, USER, PWD);

		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			System.exit(1);

		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
		return connection;
	}
	
}