package json_generation_PA;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Json_General_Main {
	/**
     * Generate Json data
     * @param 	jsonobjall
     * @author   Wing.Xu
     * @Date creation   8/15/2016 4:14:35 PM
     * @Date modification   
     */

	public static void main(String[] argv) throws SQLException {
		/** time **/
		System.out.println(System.currentTimeMillis());
		long starttime = System.currentTimeMillis();
		
		StringBuilder sqlroot = new StringBuilder();
		//sqlroot.append("SELECT PARENT_TAB,CHILD_PK,CHILD_TAB,CHILD_COL,COL_LABLE,ALL_COLS,ALL_ATTRS,DATA_TYPE FROM ").append(Public_Variable.cfgtab).append(" WHERE STATUS = 1 AND PARENT_TAB IS NULL");
		sqlroot.append("SELECT CHILD_TAB,CHILD_COL FROM ").append(Public_Variable.rootcoftab).append(" WHERE STATUS = 1 AND PARENT_TAB IS NULL");
		PreparedStatement pstatroottab = null;
		ResultSet rsroottab = null;
		try{
			pstatroottab = Public_Variable.connection.prepareStatement(sqlroot.toString());
			rsroottab = pstatroottab.executeQuery();
			while(rsroottab.next()){
				String rootTab = rsroottab.getString("CHILD_TAB");
				String rootCol = rsroottab.getString("CHILD_COL");
				//String rootCols = rsroottab.getString("ALL_COLS");
				//String rootAttrs = rsroottab.getString("ALL_ATTRS");
				//add by wing to capture data type 20170301
				//String rootDataTypes = rsroottab.getString("DATA_TYPE");
				//String roottype = rsroottab.getString("TYPE_MARK");
				
				//Json_Get_Root.JSONRoot(rootTab,rootCol,rootCols,rootAttrs);
				//Json_Get_Root.JSONRoot(rootTab,rootCol,rootCols,rootAttrs,rootDataTypes);
				Json_Get_Product.JSONProductCode(rootTab, rootCol);
			}
			//rsroottab.close();
			//pstatroottab.close();
			/** time **/
			System.out.println(System.currentTimeMillis());
			long endtime = System.currentTimeMillis();
			System.out.println((endtime-starttime)/1000+"s");
		}catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (rsroottab != null) {
				rsroottab.close();
			}
			if (pstatroottab != null) {
				pstatroottab.close();
			}
		}
	}
	
}