package com.servicenow.apis;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
/*import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;*/
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.servicenow.config.ApiPaths;
import com.servicenow.config.AppConfiguration;
import com.servicenow.utils.Base64EncodingExample;

public class ServiceNowAPIS {

	public String getTestDetails(String url,String path,String jsonfilterparameter) throws ParseException, IOException  {
		
	       String responseBody=null;
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
     credsProvider.setCredentials(/*https://dev23996.service-now.com/ */ 
            new AuthScope(new HttpHost(AppConfiguration.httpHost)),
             new UsernamePasswordCredentials(AppConfiguration.apiUserName, AppConfiguration.apiPWd));
     CloseableHttpClient httpclient = HttpClients.custom()
             .setDefaultCredentialsProvider(credsProvider)
             .build(); 

     try {
     	System.out.println("base url is ---"+url+path);
         HttpGet httpget = new HttpGet(url+path+jsonfilterparameter);
         httpget.setHeader("Accept", "application/json");          
         System.out.println("Executing request " + httpget.getRequestLine());
         CloseableHttpResponse response = httpclient.execute(httpget);
         try {
             System.out.println("-------------------------------------");
             System.out.println(response.getStatusLine());
        
               responseBody = EntityUtils.toString(response.getEntity());
               System.out.println("-------------------------"+responseBody);
      
         }catch(Exception e){
         	
         }
         
     } finally {
  
         httpclient.close();
     }
		return responseBody;


	}
	public String updateTestCaseLastRunStatus(HashMap<String, String> testcaseinfo, String tcid, String path,
			String pathLastRunTest, String statusjson) throws ParseException, IOException {

		String responseBody = null;
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(/* https://dev23996.service-now.com/ */
				new AuthScope(new HttpHost(AppConfiguration.httpHost)),
				new UsernamePasswordCredentials(AppConfiguration.apiUserName, AppConfiguration.apiPWd));
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();

		HttpPut httpput;
		try {
			if (path.equals(ApiPaths.table_u_test_step)) {
				System.out.println(
						"base url is ---" + ApiPaths.baseUrl + path + testcaseinfo.get("sys_id") + pathLastRunTest);

				httpput = new HttpPut(ApiPaths.baseUrl + path + testcaseinfo.get("sys_id") + pathLastRunTest);
				httpput.addHeader("Content-Type", "application/json");
				StringEntity entity = new StringEntity(statusjson);
				httpput.setEntity(entity);

				System.out.println("Executing request " + httpput.getRequestLine());
				CloseableHttpResponse response = httpclient.execute(httpput);
				System.out.println("-------------------------------------");
				System.out.println(response.getStatusLine());

				responseBody = EntityUtils.toString(response.getEntity());
				System.out.println("-------------------------" + responseBody);
			} else if (path.equals(ApiPaths.table_u_test_case)) {

				System.out.println("base url is ---" + ApiPaths.baseUrl + path + tcid + pathLastRunTest);

				httpput = new HttpPut(ApiPaths.baseUrl + path + tcid + pathLastRunTest);
				httpput.addHeader("Content-Type", "application/json");
				StringEntity entity = new StringEntity(statusjson);
				httpput.setEntity(entity);

				System.out.println("Executing request " + httpput.getRequestLine());
				CloseableHttpResponse response = httpclient.execute(httpput);
				System.out.println("-------------------------------------");
				System.out.println(response.getStatusLine());

				responseBody = EntityUtils.toString(response.getEntity());
				System.out.println("-------------------------" + responseBody);

			}

		} catch (Exception e) {

		}

		finally {

			httpclient.close();
		}
		return responseBody;

	}

	public String updateTestCaseLastRunStatus(HashMap<String, String> testcaseinfo, String path, String pathLastRunTest,
			String statusjson) throws ParseException, IOException {

		String responseBody = null;

		CredentialsProvider credsProvider = new BasicCredentialsProvider();

		credsProvider.setCredentials(/* https://dev23996.service-now.com/ */

				new AuthScope(new HttpHost(AppConfiguration.httpHost)),

				new UsernamePasswordCredentials(AppConfiguration.apiUserName, AppConfiguration.apiPWd));

		CloseableHttpClient httpclient = HttpClients.custom()

				.setDefaultCredentialsProvider(credsProvider)

				.build();

		HttpPut httpput;

		try {

			if (path.equals(ApiPaths.table_u_test_step)) {

				System.out.println("base url is ---" + ApiPaths.baseUrl + path + testcaseinfo.get("sys_id")

						+ pathLastRunTest);

				httpput = new HttpPut(ApiPaths.baseUrl + path + testcaseinfo.get("sys_id")

						+ pathLastRunTest);

				httpput.addHeader("Content-Type", "application/json");

				StringEntity entity = new StringEntity(statusjson);

				httpput.setEntity(entity);

				System.out.println("Executing request " + httpput.getRequestLine());

				CloseableHttpResponse response = httpclient.execute(httpput);

				System.out.println("-------------------------------------");

				System.out.println(response.getStatusLine());

				responseBody = EntityUtils.toString(response.getEntity());

				System.out.println("-------------------------" + responseBody);

			} else if (path.equals(ApiPaths.table_u_test_case)) {

				System.out.println("base url is ---" + ApiPaths.baseUrl + path + testcaseinfo.get("tcid")

						+ pathLastRunTest);

				httpput = new HttpPut(ApiPaths.baseUrl + path + testcaseinfo.get("tcid")

						+ pathLastRunTest);

				httpput.addHeader("Content-Type", "application/json");

				StringEntity entity = new StringEntity(statusjson);

				httpput.setEntity(entity);

				System.out.println("Executing request " + httpput.getRequestLine());

				CloseableHttpResponse response = httpclient.execute(httpput);

				System.out.println("-------------------------------------");

				System.out.println(response.getStatusLine());

				responseBody = EntityUtils.toString(response.getEntity());

				System.out.println("-------------------------" + responseBody);

			}

		} catch (Exception e) {

		}

		finally {

			httpclient.close();

		}

		return responseBody;

	}

	/*
	 * public HashMap< String, Object> jsontoMap(String json ) throws
	 * JsonParseException, JsonMappingException, IOException{
	 * 
	 * ObjectMapper mapper = new ObjectMapper(); // String json =
	 * "{\"name\":\"mkyong\", \"age\":29}";
	 * 
	 * HashMap<String, Object> map = new HashMap<String, Object>();
	 * 
	 * // convert JSON string to Map map = mapper.readValue(json, new
	 * TypeReference<Map<String, String>>(){});
	 * 
	 * System.out.println(map); return map;
	 * 
	 * }
	 */

	public String fileattachedTestCaseLastRunStatus(String filepath, String fname, String u_test_case)
			throws ParseException, IOException, JSONException {
		Base64EncodingExample be = new Base64EncodingExample();
		String responseBody = null;
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials( /* https://dev23996.service-now.com/ */
				new AuthScope(new HttpHost(AppConfiguration.httpHost)),
				new UsernamePasswordCredentials(AppConfiguration.apiUserName, AppConfiguration.apiPWd));
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
		String file = filepath + "\\" + fname + ".pdf";
		try {

			HttpPost httppost = new HttpPost("https://dev23996.service-now.com/api/now/table/ecc_queue");
			/*
			 * "https://dev23996.service-now.com/ecc_queue.do?JSON&sysparm_action=insert"
			 * );
			 */

			httppost.addHeader("Content-Type", "application/json");

			String strencodefiletobase64binary = be.encodeFileToBase64Binary(file);

			StringEntity entity = new StringEntity(
					createJSON(fname + ".pdf", u_test_case, strencodefiletobase64binary));
			System.out.println("------Entity-----" + entity);

			httppost.setEntity(entity);

			System.out.println("Executing request " + httppost.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				System.out.println("-------------------------------------");
				System.out.println(response.getStatusLine());

				responseBody = EntityUtils.toString(response.getEntity());
				System.out.println("-------------------------" + responseBody);
				// 16726623db00760075277befbf961930
			} catch (Exception e) {

			}

		} finally {

			httpclient.close();
		}
		return responseBody;
	}

	public String createJSON(String testcase, String tcid, String strencodefiletobase64binary) throws JSONException {

		JSONObject json = new JSONObject();
		json.put("agent", "AttachmentCreator");
		json.put("topic", "AttachmentCreator");
		json.put("name", testcase + ": application / pdf");
		json.put("source", "u_test_case:" + tcid);
		json.put("payload", strencodefiletobase64binary);
		System.out.println("JSON is Created !!!" + json.toString());
		return json.toString();

	}
 
 

}