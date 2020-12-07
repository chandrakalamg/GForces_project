package com.projects.testcases;

import java.text.ParseException;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.projects.base.BaseTest;
import com.projects.reports.Extentreports;

import dataprovider.TestDataProvider;
//Inherit the base class
public class EditandsaveComputer extends BaseTest{
	//Initializing the report
	@BeforeMethod
	public void init_report() {
		reports = Extentreports.getReports();
		test = reports.createTest("EditandsaveComputer");
	}
	//Filter the records and open the Edit computer page
	@Test(priority=1, dataProviderClass = TestDataProvider.class,dataProvider = "getData")
	public void EditComputerDetails(String computername, String intro_dt, String discontnued_dt, String companyname) throws ParseException{
		reportInfo("EditComputerDetails started");
		type("search_xpath",computername);
		click("filterbtn_xpath");
		
		VerifyRecordIntable("table_xpath",computername,intro_dt,discontnued_dt,companyname);
		//click(computername);
		driver.findElement(By.linkText(computername)).click();
		verifyTitle("Editpage");	
	}
	//Update select few values and save, Verify the update message in home page
	@Test(priority=2, dataProviderClass = TestDataProvider.class,dataProvider = "getData")
	public void EditdataAndSave(String nameedit,String companyname){
		reportInfo("EditdataAndSave started");
		type("name_xpath",nameedit);
		select("companyname_name",companyname);
		
		click("savebtn_xpath");
		//verifymessage("msg_xpath",prop.getProperty("addmessage")+" "+nameedit+" has been updated");
		String actualmsg = verifymessage("msg_xpath");
		if(actualmsg.contentEquals(prop.getProperty("addmessage")+" "+nameedit+" has been updated")){
			reportPass("Message displayed : " +prop.getProperty("addmessage")+" "+nameedit+" has been updated");
		} else {
			reportFailure("Message not displayed");
		}
	}
	//Verify the record count has not changed
	@Test(priority=3)
	public void verify(){
		reportInfo("EditdataAndSave started");
		String title_msg = verifyTitle("mainpage");
		if(title_msg.contains(prop.getProperty("mainpage"))){
			reportPass("Title displayed : " +prop.getProperty("mainpage"));
		} else {
			reportFailure("Title not displayed");
		}
		String rec_after = getrecord(title_msg);	
		int records =Integer.parseInt(rec_after);
		if(records== Integer.parseInt(records_before)) {
			reportPass("1 record has been updated. Total Records :"+records);
		}
		
	}

}
