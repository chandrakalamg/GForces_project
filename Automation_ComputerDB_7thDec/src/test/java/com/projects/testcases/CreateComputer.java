package com.projects.testcases;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.projects.base.BaseTest;
import com.projects.reports.Extentreports;

import dataprovider.TestDataProvider;
//Inheriting the BaseTest class
public class CreateComputer extends BaseTest{
	//Initializing TestNG report
	@BeforeMethod
	public void init_report() {
		reports = Extentreports.getReports();
		test = reports.createTest("CreateComputer");
	}
	//Open Add a new computer page
	@Test(priority=1)
	public void AddComputer(){
		//test = reports.createTest("AddComputer");
		reportInfo("Add Computer started");
			click("addbtn_xpath");
			reportInfo("Clicking on Add computer button");
			verifyTitle("addpage");
			//reportPass("Add computer page is displayed");
	}
	//Enter all the mandatory inputs 
	@Test(priority=2, dataProviderClass = TestDataProvider.class,dataProvider = "getData")
	public void Inputdata(String computername, String intro_dt, String discontnued_dt, String companyname){
		//init();
		reportInfo("Input data started");
		type("name_xpath",computername);
		type("introduced_dt_xpath",intro_dt);
		type("discontnued_dt_xpath",discontnued_dt);
		select("companyname_name",companyname);
		reportInfo("Data Input is successfull");
	}
	
	
	//Create the computer and verify the Record and count from the homepage table
	@Test(priority=3, dataProviderClass = TestDataProvider.class,dataProvider = "getData")
	public void clickandverify(String computername){
		reportInfo("clickandverify started");
		click("createbtn_xpath");
		
		String actualmsg = verifymessage("msg_xpath");
		
		if(actualmsg.contentEquals(prop.getProperty("addmessage")+" "+computername+" has been created")){
			reportPass("Message displayed : " +prop.getProperty("addmessage")+" "+computername+" has been created");
		} else {
			reportFailure("Message not displayed");
		}
		//verify Title
		String title_msg = verifyTitle("mainpage");
		if(title_msg.contains(prop.getProperty("mainpage"))){
			reportPass("Title displayed : " +prop.getProperty("mainpage"));
		} else {
			reportFailure("Title not displayed");
		}
		String rec_after = getrecord(title_msg);	
		int records =Integer.parseInt(rec_after);
		if(records== Integer.parseInt(records_before)+1) {
			reportPass("1 record has been created. Total Records :"+records);
			//System.out.println("1 record has been created. Total Records :"+records);
		}
		reportInfo("Test CreateComputer Ended");
	}
	
	
	
}
