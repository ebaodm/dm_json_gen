package json_call_API;
//import java.io.InputStream;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

//import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class MigrationResourseTest {

	public void CallAPI() throws Exception {
		Pub_Variable publicvari=new Pub_Variable();
		String url = 
				//MNC_DC
				publicvari.callURL;  
				//ERGO_UAT2
				//"http://172.25.12.122/restlet/v1/pa/migration/policy";
		RestTemplate restTemplate = new RestTemplate();
		// set http headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		// TODO
		/*String accessToken = getAccessToken(); marked by wing for not going to use token 20170314
		 String accessToken=
		 "TGT-7-3gBAJ5mMjwyKrkaCYnjzm7EoNyjQK5jM9JtYSwwZrxQafIe7tS-cas01.example.org";
		System.out.println("*******************" + accessToken); marked by wing for not going to use token 20170314
		headers.add("Authorization", "Bearer " + accessToken);*/
		headers.add("Authorization", "Basic QURNSU46ZUJhbzEyMzQ= ");

		/** get json from DB **/
		// FOR PARALlEL
		// String sqljson =
		// "SELECT PK_ID,JSON_COL FROM T_JSON WHERE IS_POST IS NULL AND PK_ID BETWEEN ? AND ? ";
		String sqljson = "SELECT PK_ID,JSON_COL FROM T_JSON WHERE IS_POST IS NULL";
		PreparedStatement pstatement = null;
		// pstatement.setInt(1, 1);
		// pstatement.setInt(2, 2);
		ResultSet rs = null;
		try {
			pstatement = Pub_Variable.connection.prepareStatement(sqljson);
			rs = pstatement.executeQuery();
			while (rs.next()) {

				String policyid = rs.getString("PK_ID");
				String policyJson = rs.getString("JSON_COL");
				//System.out.println("^^^^^^^^^^^^policyJson" + policyJson);
				
				String result = null;				
				result = policyJson;

				try {
					HttpEntity<String> requestEntity = new HttpEntity<String>(policyJson, headers);
					result = restTemplate.postForObject(url, requestEntity, String.class);
					if (result.getBytes("utf-8").length < 4000){
						Result_Insert_DB.ResultToDB(policyid, result, "Y");
					}
					else{
						Result_Insert_DB.ResultToDB(policyid, result.substring(0,3999), "Y");
					}
					//System.out.println("^^^^^^^^^^^^" + result);
				} catch (Exception e) {
				
					result = e.getMessage()+" "+Public_Tools.getExceptionInfo(e);
					if(result.getBytes("utf-8").length < 4000){
						Result_Insert_DB.ResultToDB(policyid, result, "N");
					}
					else{
						Result_Insert_DB.ResultToDB(policyid, result.substring(0,3999), "N");
					}

				}
				//
			}
		} catch (SQLException ex) {
			System.out.println(sqljson);
			ex.printStackTrace();
			//System.exit(1);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstatement != null) {
				pstatement.close();
			}
		}

	}

	/*public String getAccessToken() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		 String url =
		// "http://172.25.17.213:8091/cas-server/v1/tickets?username=ADMIN&password=eBao1234";
		//MNC_DC
		 "http://172.25.16.125/cas-server/v1/tickets?username=ADMIN&password=eBao1234";
		//"http://172.25.12.122/cas-server/v1/tickets?username=ADMIN&password=eBao1234";
		ResponseEntity<String> response = restTemplate.exchange(url,
				HttpMethod.POST, null, String.class);
		String accessToken = response.getBody().toString();
		accessToken = accessToken.replace("{\"access_token\":\"", "");
		accessToken = accessToken.replace("\"}", "");
		System.out.println("*******************" + accessToken);
		accessToken = "TGT-34-Tg241FtQDB5eMXL19GtP4MzkdzmCgupQcHPMfGC04YwT0LOWPn-cas01.example.org";
		return accessToken;

	}*/

	public static void main(String args[]) throws Exception {
		System.out.println("I am Ready!");
		long starttime = System.currentTimeMillis();
		MigrationResourseTest migrationResourseTest = new MigrationResourseTest();
		migrationResourseTest.CallAPI();
		long endtime = System.currentTimeMillis();
		System.out.println((endtime - starttime) / 1000 + "s");
	}

}
