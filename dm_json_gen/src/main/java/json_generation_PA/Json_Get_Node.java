package json_generation_PA;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

public class Json_Get_Node {
	/**
     * Get Node for Json
     * @param roottable, value, jsonroot
     * @return 
     * @author   Wing.Xu
     * @Date creation  8/15/2016 4:14:35 PM
     * @Date modification  
     */
	
	public static void SubTable(String roottable, String prdcode,String poltype, String value, JSONObject jsonroot) throws SQLException{
		/** get sub table **/
		
		StringBuilder sqlsub = new StringBuilder();
		Date subvaluedate = new Date();
		/** bind variable **/
		sqlsub.append("SELECT PARENT_TAB,CHILD_PK,CHILD_TAB,CHILD_COL,COL_LABLE,LISTAGG(ALL_COLS, ',') WITHIN GROUP(ORDER BY PRODUCT_CODE,POLICY_TYPE) ALL_COLS,LISTAGG(ALL_ATTRS, ',') WITHIN GROUP(ORDER BY PRODUCT_CODE,POLICY_TYPE) ALL_ATTRS,LISTAGG(DATA_TYPE, ',') WITHIN GROUP(ORDER BY PRODUCT_CODE,POLICY_TYPE) DATA_TYPE  FROM ").append(Public_Variable.cfgtab).append(" WHERE STATUS = 1 AND PARENT_TAB = ? AND (PRODUCT_CODE = 'ALL' OR INSTR(PRODUCT_CODE, ?) > 0) AND (POLICY_TYPE = 'ALL' OR INSTR(POLICY_TYPE, ?) > 0) GROUP BY PARENT_TAB,CHILD_PK,CHILD_TAB,CHILD_COL,COL_LABLE");
	
		PreparedStatement pstatsub = Public_Variable.connection.prepareStatement(sqlsub.toString());
		pstatsub.setString(1, roottable);
		pstatsub.setString(2, prdcode);
		pstatsub.setString(3, poltype);
		ResultSet rssub = pstatsub.executeQuery();
		/** no bind variable **/
		//sqlsub.append("SELECT PARENT_TAB,CHILD_PK,CHILD_TAB,CHILD_COL,COL_LABLE FROM ").append(cfgtab).append(" WHERE STATUS = 1 AND PARENT_TAB = '").append(roottable).append("' ORDER BY REMARK");
		//Statement statsub = connection.createStatement();
		//ResultSet rssub = statsub.executeQuery(sqlsub.toString());
		
		
		while(rssub.next()){
				String subcolp = rssub.getString("CHILD_PK");
				String subtab = rssub.getString("CHILD_TAB");
				String subcol = rssub.getString("CHILD_COL");
				String collab = rssub.getString("COL_LABLE");
				String subcols = rssub.getString("ALL_COLS");
				String subattrs = rssub.getString("ALL_ATTRS");
				//add by wing to capture data type 20170308
				String subdatatypes = rssub.getString("DATA_TYPE");
				StringBuilder sqlsubvalue = new StringBuilder();
				/** get sub vaule **/
					/** bind variable **/
				sqlsubvalue.append("SELECT ").append(subcols).append(" FROM ").append(subtab).append(" WHERE ").append(subcol).append(" = ?");
				PreparedStatement pstatsubvalue = Public_Variable.connection.prepareStatement(sqlsubvalue.toString(),ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				pstatsubvalue.setString(1, value);
				ResultSet rssubvalue = pstatsubvalue.executeQuery();
					/** no bind variable **/
//				sqlsubvalue.append("SELECT * FROM ").append(subtab).append(" WHERE ").append(subcol).append(" = '").append(value).append("'");
//				Statement statsubvalue = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
//				ResultSet rssubvalue = null;
//				rssubvalue = statsubvalue.executeQuery(sqlsubvalue.toString());
												
				ResultSetMetaData metaData = rssubvalue.getMetaData();
				int colCount = metaData.getColumnCount();
				String[] subattrArr = subattrs.split(",");
				String[] subdataType = subdatatypes.split(",");
				/** array **/
				JSONArray array = new JSONArray(); 		
				while (rssubvalue.next()) {					
					JSONObject jsonobj = new JSONObject();

					String nextValue = rssubvalue.getString(ColumnConvert.FirstUpperCase(subcolp));
					for (int i = 1; i <= colCount; i++) {
			              String columnName =metaData.getColumnLabel(i); 
			              String attrName = subattrArr[i-1];
			              //modified by wing for date convert 20170308
			              String sdatatype = subdataType[i-1];
			              if (sdatatype.equals("Date")) {
			            	  subvaluedate = rssubvalue.getDate(columnName);
			                  if (!"".equals(subvaluedate) && subvaluedate != null) {
		 	                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			                  
		 	                    jsonobj.put(attrName, sdf.format(subvaluedate));
			                  }
			                }
			                else{
			                  String colvalue = rssubvalue.getString(columnName);
			                  jsonobj.put(attrName, colvalue); 
			                }
			                        
			              /*//do not consider about data type
			              String colvalue = rssubvalue.getString(columnName);
			              jsonobj.put(attrName, colvalue);*/
			    	}
					array.put(jsonobj);
					jsonroot.put(collab, array);
					//System.out.println("3:" + jsonroot);
					/** recursion **/
					SubTable(subtab,prdcode,poltype,nextValue,jsonobj);
				}
				/** remove blank node **/
				rssubvalue.last();
				if (rssubvalue.getRow() == 0) {
					jsonroot.remove(collab);
				};
				rssubvalue.close();
				pstatsubvalue.close();
		}	
		rssub.close();
		pstatsub.close();
		
	}
}
