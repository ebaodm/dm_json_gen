package json_generation_PA;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Json_Insert_DB {
	
	/* Insert Json to DB
     * @param rootcol
     * @return 
     * @author   Wing.Xu
     * @Date creation  8/15/2016 4:14:35 PM
     * @Date modification  
     */
	
	public static void JsonToDB(String rootcol) throws SQLException{
		//insert JSON to DB
		//no bind variable
		/*StringBuilder sqlInsertDB = new StringBuilder();
		sqlInsertDB.append("INSERT INTO T_JSON(PK_ID,JSON_COL) VALUES (").append("'").append(jsonobjall.getString(rootcol)).append("','").append(formatJson(jsonobjall.toString())).append("')");
    	//System.out.println(sqlInsertDB);
		Statement stateInsert = connection.createStatement();
		int InsertDB = stateInsert.executeUpdate(sqlInsertDB.toString());
		stateInsert.close();*/
	
		/** bind variable **/
		String sqlInsertDB = "INSERT INTO T_JSON(PK_ID,JSON_COL,PRODUCT_CODE,INSERT_TIME,POLICY_NO) VALUES (?,?,?,?,?)";
		PreparedStatement pstmt = Public_Variable.connection.prepareStatement(sqlInsertDB);
		/** update script to replace column name to attribute name **/
		//pstmt.setString(1, Public_Variable.jsonobjall.getString(ColumnConvert.FirstUpperCase(rootcol)));
		pstmt.setString(1, Public_Variable.jsonobjall.getString(Public_Variable.vpk));
		//pstmt.setString(2, Json_Format.formatJson(Public_Variable.jsonobjall.toString()));
		pstmt.setString(2, Public_Variable.jsonobjall.toString());
		pstmt.setString(3, Public_Variable.jsonobjall.getString(Public_Variable.prdattrname));
		pstmt.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));
		pstmt.setString(5, Public_Variable.jsonobjall.getString(Public_Variable.polnoattrname));
		try{
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException ex) {
			System.out.println(sqlInsertDB);
			ex.printStackTrace();
			pstmt.close();
			//System.exit(1);
		} 
	}
}
