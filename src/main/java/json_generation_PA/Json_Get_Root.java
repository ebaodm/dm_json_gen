package json_generation_PA;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.json.JSONObject;

public class Json_Get_Root {
	 
	/* Get Json for Root Table (Main Table)
     * @param prdcode,roottab,rootcol,rootcols,rootattrs,rootdatatypes
     * @return 
     * @author   Wing.Xu
     * @Date creation  8/15/2016 4:14:35 PM
     * @Date modification  
     */

	public static void JSONRoot(String prdcode,String poltype,String polid,String roottab,String rootcol,String rootcols,String rootattrs,String rootdatatypes) throws SQLException{
		
		String value = new String();
		Date valuedate = new Date();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(rootcols).append(" FROM ").append(roottab).append(" WHERE ").append(rootcol).append(" = ?");

		PreparedStatement pstatement = Public_Variable.connection.prepareStatement(sql.toString());
		pstatement.setString(1, polid);
		
		ResultSet rs = pstatement.executeQuery();
		
		ResultSetMetaData metaData = rs.getMetaData();  
		int colCount = metaData.getColumnCount();
		String[] rootattrArr = rootattrs.split(",");
		String[] rootDataType = rootdatatypes.split(",");
		
		while(rs.next()){
			Public_Variable.jsonobjall = new JSONObject();
			for (int i = 1; i <= colCount; i++) {  
	              String columnName =metaData.getColumnLabel(i);
	              String rootattrName = rootattrArr[i-1];	              
	              //modified by wing for date convert 20170301
	              String datatype = rootDataType[i-1];
	              if (datatype.equals("Date")) {
	                  valuedate = rs.getDate(columnName);
	                  if (!"".equals(valuedate) && valuedate != null) {
 	                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	                  
	                    //Public_Variable.jsonobjall.put(columnName, value);
	                    Public_Variable.jsonobjall.put(rootattrName, sdf.format(valuedate));
	                    //System.out.println(columnName + ":" + sdf.format(valuedate));
	                  }
	                }
	                else{
	                  value = rs.getString(columnName);
	                  Public_Variable.jsonobjall.put(rootattrName, value); 
	                }
	                
	              if(columnName .equals(rootcol)){
	          		  /** get sub table **/
	            	  Json_Get_Node.SubTable(roottab, prdcode,poltype, value, Public_Variable.jsonobjall);
	              }
	    	}			
			Json_Insert_DB.JsonToDB(rootcol);		
		}
		
		rs.close();
		pstatement.close();
	}
}
