package com.servicenow.config;

import java.io.File;

public interface ConstantsValue {
 
	public String filepathScreenshot=System.getProperty("user.dir")+ /*File.separatorChar + "Cucumber"+ */File.separatorChar+"Screenshots"+File.separatorChar;
	public String filepath=System.getProperty("user.dir")+ File.separatorChar + "Cucumber"+ File.separatorChar  + "csv"+ File.separatorChar;
/*String displayTestSuiteparmaeters="?sysparm_query=u_active%3Dtrue%5EORDERBYu_number%5EORDERBYu_order&sysparm_fields=u_name%2Csys_id%2Cu_number%2Cu_active";
String displayTestCaseparmaeters="?sysparm_query=u_active%3Dtrue%5EORDERBYu_number%5EORDERBYu_order&sysparm_fields=u_name%2Csys_id%2Cu_number%2Cu_order%2Cu_active%2Cu_test_suite%2Csys_id%2Cu_description%3Dtrue&sysparm_exclude_reference_link=true";
String displayTestStepparameters="?sysparm_query=u_active%3Dtrue%5EORDERBYu_number%5EORDERBYu_order&sysparm_fields=u_name%2Cu_number%2Cu_order%2Cu_value%2Cu_step_type.u_name%2Cu_test_case%2Cu_field_name%2Cu_active%3Dtrue&sysparm_exclude_reference_link=true";
*/

String displayTestSuiteparmaeters="?sysparm_query=u_active%3Dtrue%5EORDERBYu_number%5EORDERBYu_order&sysparm_fields=u_name%2Csys_id%2Cu_number%2Cu_active";
String displayTestCaseparmaeters="?sysparm_query=u_active%3Dtrue%5EORDERBYu_number%5EORDERBYu_order&sysparm_fields=u_name%2Csys_id%2Cu_number%2Cu_order%2Cu_active%2Cu_test_suite%2Csys_id%2Cu_description%3Dtrue&sysparm_exclude_reference_link=true";
String displayTestStepparameters="?sysparm_query=u_active%3Dtrue%5EORDERBYu_test_case%5EORDERBYu_order%5EORDERBYu_test_case&sysparm_fields=u_name%2Csys_id%2Cu_number%2Cu_order%2Cu_active%2Cu_value%2Cu_step_type.u_name%2Cu_test_case%2Cu_field_name%2Cu_active%3Dtrue&sysparm_exclude_reference_link=true";
 
public  String TestDataFile=System.getProperty("user.dir")+ /*File.separatorChar + "Cucumber"+*/ File.separatorChar+"TestData"+ File.separatorChar+"test.xlsx";;
}







