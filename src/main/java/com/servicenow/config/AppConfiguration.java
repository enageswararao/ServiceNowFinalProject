package com.servicenow.config;

import java.io.File;

public interface AppConfiguration {
	   public String baseUrl="https://dev16950.service-now.com";
	    public String httpHost="dev16950.service-now.com";
	    public String apiUserName="vishal.bansal";
	    public String apiPWd="Wipro@123";
    public String filepathexcel=System.getProperty("user.dir")+ File.separatorChar+"Cucumber"+File.separatorChar +"TestData"+ File.separatorChar +"test.xlsx";
    public String pdffilepath=System.getProperty("user.dir")+ /*File.separatorChar+"Cucumber"+*/File.separatorChar +"PDFResult"+ File.separatorChar;
    
    
  
}
