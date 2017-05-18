package json_generation_PA;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Json_Get_Product {
	/* Get Product code for Root Table (Main Table)
     * @param roottab,rootcol
     * @return 
     * @author   Wing.Xu
     * @Date creation  3/20/2017 2:14:35 PM
     * @Date modification  
     */
	public static void JSONProductCode(String roottab,String rootcol) throws SQLException{
		StringBuilder sqlprd = new StringBuilder();
		//String prdcode = new String();
		//StringBuilder sqlrootprd = new StringBuilder();
		
		sqlprd.append("SELECT ").append(Public_Variable.prdcolname).append(", ").append(Public_Variable.poltypecol).append(", ").append(rootcol).append(" FROM ").append(roottab).append(" WHERE NOT EXISTS (SELECT * FROM T_JSON B WHERE B.IS_POST = 'Y' AND JPK = B.PK_ID)");
		PreparedStatement pstatementprd = Public_Variable.connection.prepareStatement(sqlprd.toString());
		ResultSet rsprd = pstatementprd.executeQuery();
		
		while(rsprd.next()){
			
			String prdcode = rsprd.getString(Public_Variable.prdcolname);
			String poltype = rsprd.getString(Public_Variable.poltypecol);
			String polid = rsprd.getString(rootcol);
			StringBuilder sqlrootprd = new StringBuilder();
			sqlrootprd.append("SELECT CHILD_TAB,CHILD_COL,COL_LABLE,LISTAGG(ALL_COLS, ',') WITHIN GROUP(ORDER BY PRODUCT_CODE,POLICY_TYPE) ALL_COLS,LISTAGG(ALL_ATTRS, ',') WITHIN GROUP(ORDER BY PRODUCT_CODE,POLICY_TYPE) ALL_ATTRS,LISTAGG(DATA_TYPE, ',') WITHIN GROUP(ORDER BY PRODUCT_CODE,POLICY_TYPE) DATA_TYPE  FROM ").append(Public_Variable.cfgtab).append(" WHERE STATUS = 1 AND CHILD_TAB = ? AND (PRODUCT_CODE = 'ALL' OR INSTR(PRODUCT_CODE, ?) > 0) AND (POLICY_TYPE = 'ALL' OR INSTR(POLICY_TYPE, ?) > 0) GROUP BY PARENT_TAB,CHILD_PK,CHILD_TAB,CHILD_COL,COL_LABLE");
			PreparedStatement pstatprd = Public_Variable.connection.prepareStatement(sqlrootprd.toString());
			
			pstatprd.setString(1, roottab);
			pstatprd.setString(2, prdcode);
			pstatprd.setString(3, poltype);
						
			ResultSet rspsprd = pstatprd.executeQuery();
			
			while(rspsprd.next()){
				String rootcols = rspsprd.getString("ALL_COLS");
				String rootattrs = rspsprd.getString("ALL_ATTRS");
				String rootdatatypes = rspsprd.getString("DATA_TYPE");
				
				Json_Get_Root.JSONRoot(prdcode,poltype,polid,roottab, rootcol, rootcols, rootattrs, rootdatatypes);
			}	
			rspsprd.close();
			pstatprd.close();
		}
		

		rsprd.close();
		pstatementprd.close();
	}
	
}
