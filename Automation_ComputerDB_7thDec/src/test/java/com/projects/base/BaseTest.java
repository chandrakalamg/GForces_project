package com.projects.base;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.projects.reports.Extentreports;

//This class contains all the reusable methods
public class BaseTest {

	public WebDriver driver;
	public Properties prop;
	public String records_before;
	public String cellval;
	public String date_intro;
	public String date_disc;
	//declaring for customized TestNG reports
	public ExtentReports reports;
	public ExtentTest test;
	
	//----------------Intialization of properties file---------------//
	//Input - Nothing
	//Output - Loads properties in an object
	public void init(){
		//init the prop file
		if(prop==null){
			prop=new Properties();
			try {
				FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//project.properties");
				prop.load(fs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//**********************Launch any browser*************************//
	//Input - Browser name to be provided in properties file
	//Output - Nothing
	public void openBrowser(String browserType){
		//DesiredCapabilities cap=null;
		String browser=prop.getProperty(browserType);
		System.out.println("Application launched "+ browser);
		if(browser.equals("Chrome")){	
			System.setProperty("webdriver.chrome.driver", prop.getProperty("chromedriver_exe"));
			driver=new ChromeDriver();
		}
		else if (browser.equals("Firefox")){
			System.setProperty("webdriver.gecko.driver", prop.getProperty("firfox_exe"));
			driver= new FirefoxDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	

	//**********************Navigate to application URL*************************//
	//Input - Application URL to be provided in properties file
	//Output - Nothing
	public void navigate(String urlKey){
		driver.get(prop.getProperty(urlKey));
	}
	

	//**********************Verify Confirmation messages for Creation/deletion & update of records*************************//
	//Input - Message element's locator and expected message
	//Output - Prints the status to the report
	public String verifymessage(String locatorKey) {
		String msg=getElement(locatorKey).getText();
		return msg;
		/*
		if(msg.equals(message)) {
			//reportPass("Message displayed : " +msg);
			return true;
		} else {
			//reportFailure("No message is displayed");
			return false;
		}
		*/
	}

	//**********************Verify Headers of any page*************************//
	//Input - Expected Header Title
	//Output - Prints the status to the report and returns the header title
	public String verifyTitle(String title){
		//String PageTitle1 = driver.findElement(By.xpath("//*[@id='main']/h1")).getText();
		String PageTitle=getElement("title_xpath").getText();
		String ExpTitle = prop.getProperty(title);
		if (PageTitle.contains(ExpTitle)) {
			System.out.println("Page header is verified:"+ PageTitle);
		}else {
			System.out.println("Page header is not verified:"+ PageTitle);
			//reportFailure("No message is displayed");
		}
		return PageTitle;
	}
	
	//**********************Click operation on any buttons*************************//
	//Input - Locator key of button
	//Output - Nothing
	public void click(String locatorKey){
		getElement(locatorKey).click();
	}
	
	//**********************Enter data in any edit fields*************************//
	//Input - Locator key of edit field and input data
	//Output - Nothing
	public void type(String locatorKey,String data){
		//System.out.println(data);
		getElement(locatorKey).clear();
		getElement(locatorKey).sendKeys(data);
	}

	//**********************Select data in dropdowns*************************//
	//Input - Locator key of dropdown and input data
	//Output - Returns the selected value
	public String select(String locatorKey,String data){
		Select companyname = new Select(getElement(locatorKey));
		//companyname.selectByIndex(Integer.parseInt(data));
		companyname.selectByVisibleText(data);
		cellval = companyname.getFirstSelectedOption().getText();
		return cellval;
	}
	//**********************Get the total number of records from the header of homepage*************************//
	//Input - Header title message
	//Output - Returns the total number of records
	public String getrecord(String title_msg){
		String titlesplit[]=title_msg.split(" ");
		String totalRecords_1=titlesplit[0];
		return totalRecords_1;
	}

	//**************************************************
	// finding element based on types of locators and returning it
	//Input - Locator key of object
	//Output - Webelement
		public WebElement getElement(String locatorKey){
			WebElement e=null;
			try{
			if(locatorKey.endsWith("_id"))
				e = driver.findElement(By.id(prop.getProperty(locatorKey)));
			else if(locatorKey.endsWith("_name"))
				e = driver.findElement(By.name(prop.getProperty(locatorKey)));
			else if(locatorKey.endsWith("_xpath"))
				e = driver.findElement(By.xpath(prop.getProperty(locatorKey)));
			else if(locatorKey.endsWith(""))
				e = driver.findElement(By.linkText(prop.getProperty(locatorKey)));
			else{
				reportFailure("Locator not correct - " + locatorKey);
				Assert.fail("Locator not correct - " + locatorKey);
			}
			
			}catch(Exception ex){
				// fail the test and report the error
				reportFailure(ex.getMessage());
				ex.printStackTrace();
				Assert.fail("Failed the test - "+ex.getMessage());
			}
			return e;
		}
		
		//*****************************Verify if the webelement is available
		//Input - Locator key of object
		//Output - Webelement
		public boolean isElementPresent(String locatorKey){
			
			List<WebElement> elementList=null;
			if(locatorKey.endsWith("_id"))
				elementList = driver.findElements(By.id(prop.getProperty(locatorKey)));
			else if(locatorKey.endsWith("_name"))
				elementList = driver.findElements(By.name(prop.getProperty(locatorKey)));
			else if(locatorKey.endsWith("_xpath"))
				elementList = driver.findElements(By.xpath(prop.getProperty(locatorKey)));
			else{
				reportFailure("Locator not correct - " + locatorKey);
				Assert.fail("Locator not correct - " + locatorKey);
			}
			
			if(elementList.size()==0)
				return false;	
			else
				return true;
		}
		
	
		/*
		wait.until((ExpectedConditions.presenceOfElementLocated(By.linkText(searchdata))));
		try {
			driver.findElement(By.linkText(searchdata)).click();
		} catch (WebDriverException e) {
			System.out.println("element not found" +e);
		}
		*/
		
		
		
		//**********************Verify & fetch record from the table*************************//
		//Input - Locator key of object, Input Computername, Input Introduceddate, Input discontinueddate, Input Companyname
		//Output - Record existence status reported
		public boolean VerifyRecordIntable(String locatorkey,String name,String date1,String date2,String company) throws ParseException {
			WebElement mytable =getElement(locatorkey);
			 
			//System.out.println("values :"+name+date1+date2+company);
			List < WebElement > rows_table = mytable.findElements(By.tagName("tr"));
			//To calculate no of rows In table.
			int rows_count = rows_table.size();
				//Loop will execute till the last row of table.
			for (int row = 0; row < rows_count; row++) {
				//To locate columns(cells) of that specific row.
				List < WebElement > Columns_row = rows_table.get(row).findElements(By.tagName("td"));
	    	  int j = 0;
	    	  	//for(int j = 0; j< Columns_row.size(); j++) {
    	        String celtext1 = Columns_row.get(j).getText();
    	        String celtext2 = Columns_row.get(j+1).getText();
    	        String celtext3 = Columns_row.get(j+2).getText();
    	        String celtext4 = Columns_row.get(j+3).getText();
    	        if(celtext2.equals("-")){
    	      //  if(celtext2.equals("-") && celtext1.equals(name)) {
    	        	date_intro = celtext2;
    	        	//System.out.println("Record exist : Name:"+name+" Introduced date:"+date1+"Discontinued date "+date2+"CompanyName:"+company);
    	        } else {
    	        	date_intro = date_format(celtext2);
    	        	
    	        }
    	        if(celtext3.equals("-")){
    	       // if(celtext3.equals("-") && celtext1.equals(name) && celtext4.equals(company)) {
    	        	date_disc = celtext3;
    	       // 	System.out.println("Record exist : Name:"+name+" Introduced date:"+date1+"Discontinued date "+date2+"CompanyName:"+company);
    	        } else {
    	        	date_disc = date_format(celtext3);
    	        }  
    	      
    			if(celtext1.equals(name) && date_intro.equals(date1) && date_disc.equals(date2) && celtext4.equals(company)){  	
    				reportPass("Record exist : Name:"+name+" Introduced date:"+date1+"Discontinued date "+date2+"CompanyName:"+company);  	        	
    				break;    				
    			}
		
			}
			return false;
	}
		//**********************Format dates captured from application*************************//
		//Input - String input of date in "dd MMM yyyy" format
		//Output - Formatted date to "yyyy-MM-dd"
		
		public String date_format(String input) throws ParseException {
			DateFormat originalFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
			DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date = originalFormat.parse(input);
			String formattedDate = targetFormat.format(date); 
			return formattedDate;
	}	

	/*****************************TestNG Reporting functions********************************/
		//Reports Pass status
		public void reportPass(String msg){
			test.log(Status.PASS, msg);
			}
		//Reports Fail status
		public void reportFailure(String msg){
			test.log(Status.FAIL, msg);
			//takeScreenShot();
			//Assert.fail(msg);
			//driver.close();
		}
		//Reports Info status
		public void reportInfo(String msg){
			test.log(Status.INFO, msg);
		}
		
@BeforeTest
	 public void beforetest() {
		init();
		openBrowser("browser");
		driver.manage().window().maximize();
		//System.getProperty("user.dir")+
		System.getProperty("LaunchingApplication");
		//Assert.fail();
		navigate("appurl");
		String title_msg = verifyTitle("mainpage");
		records_before = getrecord(title_msg);
		//test.log(Status.INFO,"Total Records");
		System.out.println("Total records :"+records_before);
	}
	
	@AfterTest
		 public void aftertest() {
		System.out.println("****CloseApplication*******");
			driver.close();
			driver.quit();
		}


	@AfterMethod
		public void Generate_report() {
			reports.flush();
		}

}

