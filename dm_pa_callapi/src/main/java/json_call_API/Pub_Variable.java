package json_call_API;
import java.sql.Connection;
import configure.property.*;

public class Pub_Variable {
	/* Define Public Variable
     * @param 
     * @return 
     * @author   Wing.Xu
     * @Date creation  8/15/2016 4:14:35 PM
     * @Date modification  
     */
	public static SetSystemProperty configure;
//	public  Pub_Variable(){
//		System.out.println("run or not...");
//		this.configure=new SetSystemProperty("run.configure");
//	}
	
//	configure.profilepath= "run.configure";
	public static JDBC_connect jdbcconnection =  new JDBC_connect();

	public static String url = "jdbc:oracle:thin:@"+configure.readValue("db.ip")+":"+configure.readValue("db.port")+":"+configure.readValue("db.sid");
	public static  String user=configure.readValue("db.user");
	public static String pwd =configure.readValue("db.pwd"); 
	
	public static Connection connection = jdbcconnection.JDBCConnection(url,user,pwd);
	
	public static String cfgtab = "T_JSON_RESPONSE";
	public static String callURL=configure.readValue("callapi.url");
	public static String callAuth=configure.readValue("callapi.auth");

}
