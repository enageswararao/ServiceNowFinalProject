package com.servicenow.testcases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.servicenow.apis.ServiceNowAPIS;
import com.servicenow.config.ApiPaths;
import com.servicenow.config.AppConfiguration;
import com.servicenow.config.ConstantsValue;
import com.servicenow.keywords.GeneralKeywords;
import com.servicenow.keywords.KeyWords;
import com.servicenow.utils.FinalPDFReport;
import com.servicenow.utils.TestUtils;

public class ServiceNowLogin extends GeneralKeywords {
	private String sTestcaseName;
	ServiceNowAPIS spis;
	TestUtils tu;
	KeyWords app;

	@DataProvider(name = "getTestcases")
	public Object[][] getTestcases() throws ParseException, IOException, JSONException {
		ServiceNowAPIS sapis = new ServiceNowAPIS();
		String reponse = sapis.getTestDetails(AppConfiguration.baseUrl, ApiPaths.testSuites,
				ConstantsValue.displayTestSuiteparmaeters);

		JSONObject testSuit_Json_Object = new JSONObject(reponse);
		JSONArray testSuit_Json_Array = testSuit_Json_Object.getJSONArray("result");

		List<JSONObject> testCaseList = new ArrayList<JSONObject>();
		for (int i = 0; i < testSuit_Json_Array.length(); i++) {
			JSONObject suite_Object = (JSONObject) testSuit_Json_Array.get(i);
			System.out.println("suite_Object Name===>" + suite_Object.get("u_name"));
			String testcase_response = sapis.getTestDetails(AppConfiguration.baseUrl, ApiPaths.testCases,
					ConstantsValue.displayTestCaseparmaeters);
			JSONObject testcase_json = new JSONObject(testcase_response);
			JSONArray testcase_json_array = testcase_json.getJSONArray("result");
			for (int j = 0; j < testcase_json_array.length(); j++) {
				JSONObject testcase_obj = (JSONObject) testcase_json_array.get(j);
				if (suite_Object.get("sys_id").equals(testcase_obj.get("u_test_suite"))) {
					testcase_obj.put("suiteName", suite_Object.get("u_name"));
					testCaseList.add(testcase_obj);
				}
			}

		}

		Object[][] objectArray = new Object[testCaseList.size()][1];
		for (int i = 0; i < objectArray.length; i++) {
			objectArray[i][0] = testCaseList.get(i);
		}
		return objectArray;

	}

	@Test(dataProvider = "getTestcases")
	public void runAllActiveTestCases(JSONObject testcase_obj) throws Exception {

		String testcaseName = "";
		String testSuite = "";
		String tcid = "";
		System.out.println(testcase_obj.get("u_name"));
		ServiceNowAPIS sapis = new ServiceNowAPIS();
		String testSteps_response = sapis.getTestDetails(AppConfiguration.baseUrl, ApiPaths.testSteps,
				ConstantsValue.displayTestStepparameters);
		JSONObject testSteps_json = new JSONObject(testSteps_response);
		JSONArray testSteps_json_array = testSteps_json.getJSONArray("result");
		List<JSONObject> testStepsList = new ArrayList<JSONObject>();
		List<HashMap<String, String>> tc_steplist = new ArrayList<HashMap<String, String>>();
		for (int j = 0; j < testSteps_json_array.length(); j++) {
			JSONObject testStep_obj = (JSONObject) testSteps_json_array.get(j);
			if (testcase_obj.get("sys_id").equals(testStep_obj.get("u_test_case"))) {
				testcaseName = testcase_obj.getString("u_name");
				testSuite = testcase_obj.getString("suiteName");
				tcid = testStep_obj.getString("u_test_case");
				HashMap<String, String> stepList = new HashMap<String, String>();
System.out.println("Test Step--Nag-"  );
				testStepsList.add(testStep_obj);
				System.out.println("Test Step---" + testStep_obj.get("u_name"));
				System.out.println("Test Step--" + testStep_obj.get("u_step_type.u_name"));

				stepList.put("uname", testStep_obj.getString("u_name"));
				stepList.put("tcid", testStep_obj.getString("u_test_case"));
				stepList.put("sys_id", testStep_obj.getString("sys_id"));
				stepList.put("keyword", testStep_obj.getString("u_step_type.u_name"));
				stepList.put("locator", testStep_obj.getString("u_field_name"));
				stepList.put("data", testStep_obj.getString("u_value"));
				stepList.put("steporder", testStep_obj.getString("u_order"));

				tc_steplist.add(stepList);

			}

		}

		List<HashMap<String, String>> listresult = app.executeKeywords(tc_steplist);

		HashMap<String, String> tc_details = new HashMap<String, String>();
		tc_details.put("tCdesc", testcaseName);
		tc_details.put("tSdesc", testSuite);
		tc_details.put("TestCaseResult", tu.finalStepsStatus(listresult.get(3)));
		if (tu.finalStepsStatus(listresult.get(3)).equals("Pass")) {
			tc_details.put("tcid", tc_steplist.get(0).get("tcid"));
			spis.updateTestCaseLastRunStatus(listresult.get(3), tcid, ApiPaths.table_u_test_case,
					ApiPaths.pathLastRunTestStep, ApiPaths.pass);
			FinalPDFReport f = new FinalPDFReport();
			f.createResultPDF(tc_details, listresult.get(1), listresult.get(2), testcaseName);
			System.out.println("PDF generated");
		} else {
			tc_details.put("tcid", tc_steplist.get(0).get("tcid"));
			spis.updateTestCaseLastRunStatus(listresult.get(3), tcid, ApiPaths.table_u_test_case,
					ApiPaths.pathLastRunTestStep, ApiPaths.fail);
			listresult.get(0).put("TestCaseResult", tu.finalStepsStatus(listresult.get(3)));
			FinalPDFReport f = new FinalPDFReport();
			f.createResultPDF(tc_details, listresult.get(1), listresult.get(2), testcaseName);
			System.out.println("PDF generated");

		}
		spis.fileattachedTestCaseLastRunStatus(AppConfiguration.pdffilepath, testcaseName,
				tc_steplist.get(0).get("tcid"));

	}

	@BeforeMethod
	public void start() {
		tu = new TestUtils();
		spis = new ServiceNowAPIS();
		app = new KeyWords();

	}

	@AfterTest
	public void generatePDFReport() {

	}
}
