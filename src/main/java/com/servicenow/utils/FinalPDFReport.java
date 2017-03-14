package com.servicenow.utils;

 
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.servicenow.config.AppConfiguration;
 

public class FinalPDFReport {
	
	public void createResultPDF(Map<String, String> map,Map<String, String> map2,Map<String, String> map3,String testCaseName ){

		
		Font blueFont = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new CMYKColor(255, 0, 0, 0));
		Font redFont = FontFactory.getFont(FontFactory.COURIER, 12, Font.BOLD, new CMYKColor(0, 255, 0, 0));
		Font yellowFont = FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD, new CMYKColor(0, 0, 255, 0));
	    //Document document = new Document();
		
		 float left = 30;
	        float right = 30;
	        float top = 60;
	        float bottom = 0;
	        Document document = new Document(PageSize.A4, left, right, top, bottom);
	    try
	    {
	    	
	    	
	        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(AppConfiguration.pdffilepath+testCaseName+".pdf"));
	        document.open();
	        
	          List<String> list = new ArrayList<String>(map3.values());
	          List<String> list2 = new ArrayList<String>(map2.values());
	          List<String> list3 = new ArrayList<String>(map2.keySet());
	          String[] IMAGES = new String[list.size()];
	          IMAGES = list.toArray(IMAGES);
	        Image img = Image.getInstance(IMAGES[0]);
	        document.setPageSize(img);
	        //document.setPageCount(2);
	        PdfPTable table = new PdfPTable(1); // 3 columns.
	        table.setWidthPercentage(100); //Width 100%
	        table.setSpacingBefore(10f); //Space before table
	        table.setSpacingAfter(10f); //Space after table
	    
	        //Set Column widths
	        float[] columnWidths = {1f };
	        table.setWidths(columnWidths);
	        
	        PdfPCell testSuites = new PdfPCell(new Paragraph("TC_Suites :        "+map.get("tSdesc")   )); 
	        testSuites.setBorderColor(BaseColor.BLUE);
	        testSuites.setPaddingLeft(10);
	        testSuites.setHorizontalAlignment(Element.ALIGN_LEFT);
	        testSuites.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 

	        PdfPCell testCases = new PdfPCell(new Paragraph("TC_Name :           "+map.get("tCdesc") )); 
	        testCases.setBorderColor(BaseColor.BLUE);
	        testCases.setPaddingLeft(10);
	        testCases.setHorizontalAlignment(Element.ALIGN_LEFT);
	        testCases.setVerticalAlignment(Element.ALIGN_MIDDLE);
	       /* PdfPCell testSteps = new PdfPCell(new Paragraph("TC_Name :          "+testcaseDetails.get("tStepdesc") )); 
	        testSteps.setBorderColor(BaseColor.BLUE);
	        testSteps.setPaddingLeft(10);
	        testSteps.setHorizontalAlignment(Element.ALIGN_LEFT);
	        testSteps.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        */
	        PdfPCell testResult = new PdfPCell(new Paragraph("TC_Result :        "+map.get("TestCaseResult") )); 
	        testResult.setBorderColor(BaseColor.BLUE);
	        testResult.setPaddingLeft(10);
	        testResult.setHorizontalAlignment(Element.ALIGN_LEFT);
	        testResult.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        
	        Paragraph tsteps= new Paragraph(map.get("tStepdesc")) ;
	        
	        table.addCell(testSuites);
	        table.addCell(testCases);
	        //table.addCell(testSteps);
	        table.addCell(testResult);
	        document.add(table);
	        document.add(tsteps);

	       int i=0;
	       // document.open();
	        for (String image : IMAGES) {
	            img = Image.getInstance(image);

	           // document.setPageSize(img);
	            document.add(new Paragraph(list3.get(i)+" : "+list2.get(i)));
	          // document.newPage();
	           // img.setAbsolutePosition(0, 0);
	           img.scaleAbsolute(400,300);
	            document.add(img);
	            i++;
	        }
	   
	        document.close();
	        writer.close();
	    } catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	}
	
	

	public static void main(String[] args) {
	 
		HashMap<String,String> testcaseDetails= new HashMap<String,String>();
    	testcaseDetails.put("tS", "TestSuite:");
		testcaseDetails.put("tc", "TestCases:");
		testcaseDetails.put("tStep", "TestSteps:");
		
		testcaseDetails.put("tSdesc", "LoginTestSuite");
		testcaseDetails.put("tCdesc", "TC_01_LoginTestCase");
		testcaseDetails.put("tStepdesc", "TestSteps:");
		testcaseDetails.put("TestCaseResult", "Passed");
		
		LinkedHashMap<String,String> tSteps=new LinkedHashMap<String,String>();
		tSteps.put("Step1 :", "Click on the URL");
		tSteps.put("Step2 :", "Enter the UserName");
		tSteps.put("Step3 :", "Enter the Password");
		tSteps.put("Step4 :", "Click on the Submit Button");
		
		LinkedHashMap<String,String> tStepsImages=new LinkedHashMap<String,String>();
		
		tStepsImages.put("Step1", "D:\\API\\FlipkartLoginPage.png");
		tStepsImages.put("Step2", "D:\\API\\Flipkart_EnterUserName.png");
		tStepsImages.put("Step3", "D:\\API\\Flipkart_EnterPassword.png");
		tStepsImages.put("Step4", "D:\\API\\Flipkart_ClickonLoginButton.png");
		
		FinalPDFReport f=new FinalPDFReport();
	//	f.createResultPDF( testcaseDetails, tSteps,  tStepsImages );
		System.out.println("PDF generated");
		
	}

}
