package json_call_API;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Result_Insert_DB {
	
	/* Insert Result to DB
     * @param ApiResult
     * @return 
     * @author   Wing.Xu
     * @Date creation  3/15/2017 4:14:35 PM
     * @Date modification  
     */
	
	public static void ResultToDB(String PolicyId,String ApiResult,String PassFlag) throws SQLException{

		/** bind variable **/
		String sqlInsertDB = "INSERT INTO T_JSON_RESPONSE (POLICY_ID,RUN_RESULT,INSERT_TIME,IS_PASS) VALUES (?,?,?,?)";
		PreparedStatement pstmt = Pub_Variable.connection.prepareStatement(sqlInsertDB);
		
		pstmt.setString(1, PolicyId);
		pstmt.setString(2, ApiResult);
		pstmt.setTimestamp(3, new java.sql.Timestamp(new java.util.Date().getTime()));
		pstmt.setString(4, PassFlag);
		try{
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException ex) {
			System.out.println(sqlInsertDB);
			ex.printStackTrace();
			System.exit(1);
		}	
	}
}