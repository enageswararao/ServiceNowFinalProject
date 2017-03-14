package com.servicenow.keywords;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.http.ParseException;

import com.servicenow.apis.ServiceNowAPIS;
import com.servicenow.config.ApiPaths;
 

public class KeyWords {

	public List<HashMap<String, String>> executeKeywords(List<HashMap<String, String>> stepList) throws Exception {

		List<HashMap<String, String>> list = null;
		HashMap<String, String> testcaseDetails = new HashMap<String, String>();
		testcaseDetails.put("tS", "TestSuite:");
		testcaseDetails.put("tc", "TestCases:");
		testcaseDetails.put("tStep", "TestSteps:");

		LinkedHashMap<String, String> tSteps = new LinkedHashMap<String, String>();

		LinkedHashMap<String, String> tStepsImages = new LinkedHashMap<String, String>();
		LinkedHashMap<String, String> tStepstatus = new LinkedHashMap<String, String>();

		GeneralKeywords app = new GeneralKeywords();
		ServiceNowAPIS spis = new ServiceNowAPIS();
		int step = 1;

		for (int i = 0; i <stepList.size(); i++) {

			/*
			 * if(stepsinfo.get("keyword").equals("openBrowser")){
			 * 
			 * if(app.openBrowser(stepsinfo)){
			 * tStepsImages.put("Step_"+step,app.takeScreenShot(u_name));
			 * 
			 * spis.updateTestCaseLastRunStatus(stepsinfo,
			 * ApiPaths.table_u_test_step,
			 * ApiPaths.pathLastRunTestStep,ApiPaths.pass);
			 * tStepstatus.put("Step_"+step, "Pass");
			 * 
			 * }else{ tStepsImages.put("Step_"+step,app.takeScreenShot(u_name));
			 * spis.updateTestCaseLastRunStatus(stepsinfo,
			 * ApiPaths.table_u_test_step,
			 * ApiPaths.pathLastRunTestStep,ApiPaths.fail);
			 * tStepstatus.put("Step_"+step, "Fail"); }
			 * 
			 * step++; }
			 */

			tSteps.put("Step_" + step, stepList.get(i).get("uname"));

			if (stepList.get(i).get("keyword").equals("openURL")) {

				if (app.openURL(stepList.get(i).get("data"))) {
					tStepsImages.put("Step_" + step,
							app.takeScreenShot(stepList.get(i).get("uname")));

					spis.updateTestCaseLastRunStatus(stepList.get(i), ApiPaths.table_u_test_step,
							ApiPaths.pathLastRunTestStep, ApiPaths.pass);
					tStepstatus.put("Step_" + step, "Pass");

				} else {
					tStepsImages.put("Step_" + step,
							app.takeScreenShot(stepList.get(i).get("uname")));
					spis.updateTestCaseLastRunStatus(stepList.get(i), ApiPaths.table_u_test_step,
							ApiPaths.pathLastRunTestStep, ApiPaths.fail);
					tStepstatus.put("Step_" + step, "Fail");
				}

			} else if (stepList.get(i).get("keyword").equals("enterText")) {

				if (app.enterText(stepList.get(i).get("locator"), stepList.get(i).get("data"))) {
					tStepsImages.put("Step_" + step,
							app.takeScreenShot(stepList.get(i).get("uname")));

					spis.updateTestCaseLastRunStatus(stepList.get(i), ApiPaths.table_u_test_step,
							ApiPaths.pathLastRunTestStep, ApiPaths.pass);
					tStepstatus.put("Step_" + step, "Pass");
				} else {
					tStepsImages.put("Step_" + step,
							app.takeScreenShot(stepList.get(i).get("uname")));
					spis.updateTestCaseLastRunStatus(stepList.get(i), ApiPaths.table_u_test_step,
							ApiPaths.pathLastRunTestStep, ApiPaths.fail);
					tStepstatus.put("Step_" + step, "Fail");
				}

			} else if (stepList.get(i).get("keyword").equals("click")) {

				if (app.click(stepList.get(i).get("locator"))) {
					tStepsImages.put("Step_" + step,
							app.takeScreenShot(stepList.get(i).get("uname")));

					spis.updateTestCaseLastRunStatus(stepList.get(i), ApiPaths.table_u_test_step,
							ApiPaths.pathLastRunTestStep, ApiPaths.pass);
					tStepstatus.put("Step_" + step, "Pass");
				} else {
					tStepsImages.put("Step_" + step,
							app.takeScreenShot(stepList.get(i).get("uname")));
					spis.updateTestCaseLastRunStatus(stepList.get(i), ApiPaths.table_u_test_step,
							ApiPaths.pathLastRunTestStep, ApiPaths.fail);
					tStepstatus.put("Step_" + step, "Fail");
				}

			}

			else if (stepList.get(i).get("keyword").equals("verifyText")) {

				if (app.verifyText(stepList.get(i).get("locator"), stepList.get(i).get("data"))) {
					tStepsImages.put("Step_" + step,
							app.takeScreenShot(stepList.get(i).get("uname")));

					spis.updateTestCaseLastRunStatus(stepList.get(i), ApiPaths.table_u_test_step,
							ApiPaths.pathLastRunTestStep, ApiPaths.pass);
					tStepstatus.put("Step_" + step, "Pass");
				} else {
					tStepsImages.put("Step_" + step,
							app.takeScreenShot(stepList.get(i).get("uname")));
					spis.updateTestCaseLastRunStatus(stepList.get(i), ApiPaths.table_u_test_step,
							ApiPaths.pathLastRunTestStep, ApiPaths.fail);
					tStepstatus.put("Step_" + step, "Fail");
				}

			}
			step++;
		}

		list = new ArrayList<HashMap<String, String>>();
		list.add(testcaseDetails);
		list.add(tSteps);
		list.add(tStepsImages);
		list.add(tStepstatus);

		// TestSuite Level End

		return list;

	}

}
