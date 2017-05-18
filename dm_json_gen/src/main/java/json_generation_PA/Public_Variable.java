package json_generation_PA;

import java.sql.Connection;

import org.json.JSONObject;

import configure.property.SetSystemProperty;

public class Public_Variable {
	/* Define Public Variable
     * @param 
     * @return 
     * @author   Wing.Xu
     * @Date creation  8/15/2016 4:14:35 PM
     * @Date modification  
     */
	public static SetSystemProperty configure;
	public static String url = "jdbc:oracle:thin:@"+configure.readValue("db.ip")+":"+configure.readValue("db.port")+":"+configure.readValue("db.sid");
	public static  String user=configure.readValue("db.user");
	public static String pwd =configure.readValue("db.pwd"); 
	
	public static JDBC_Connection jdbcconnection =  new JDBC_Connection();
//	public static String url = "jdbc:oracle:thin:@172.16.28.46:1523:o46g4";
//	public static String user = 
//			//"ERGO_DM_SRC_2A_N1";
//			 "ergo_dm_src_2A";
//	public static String pwd = 
//			//"ERGO_DM_SRC_2A_N1";
//			 "ergo_dm_src_2Apwd";
	
	public static Connection connection = jdbcconnection.JDBCConnection(url,user,pwd);
	public static String cfgtab = "VIEW_CFG_TAB_PA";
	public static String rootcoftab = "DC_CFG_TAB_PA";    //add by wing 20170320
	//public static String prdcolname = "PRODUCT_ID";
	//product column name
	public static String prdcolname = "PRODUCTCODE";
	public static String poltypecol = "POLICYTYPE";
	//product json attribute name
	public static String prdattrname = "ProductCode";
	public static String polnoattrname = "PolicyNo";
	//public static String vpk = "@pk";
	public static String vpk = "OldPrimaryKey";
	public static JSONObject jsonobjall = new JSONObject();
}
