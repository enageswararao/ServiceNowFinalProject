package com.servicenow.utils;

import java.util.HashMap;
import java.util.Map;

public class TestUtils {
	
	
	public String finalStepsStatus(HashMap<String,String> statusMap){
		String value=null;
		//loop a Map
		for (Map.Entry<String, String> entry : statusMap.entrySet()) {
			 if(entry.getValue().equals("Pass")){
				 value="Pass";
			 }
			 
			 else{
				 value="Fail";
			 }
				 
		}
		return value;

	}

	public static void main(String[] args) {
		 

	}

}
