package dataprovider;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

public class TestDataProvider {
// Excel has not been implemented for this test suite, instead data provider is used to input data
	@DataProvider
	public static Object[][] getData(Method m){
		//System.out.println("Method / Test "+ m.getName());
		Object[][]  data=null;
		if(m.getName().equals("Inputdata")) {
			data= new Object[1][4]; //[row][col]
			// row 1
			data[0][0] = "name1";
			data[0][1] = "2020-04-10";
			data[0][2] = "2020-12-10";
			data[0][3] = "IBM";
			// row 2
		/*	data[1][0] = "Mozilla";
			data[1][1] = "SuperAdmin";
			data[1][2] = "2020-04-10";
			data[0][3] = "2020-12-10";
			data[0][4] = "IBM";
*/
		}else if(m.getName().equals("clickandverify")) {
			data= new Object[1][1]; //[row][col]
			// row 1
			data[0][0] = "name1";
		}else if(m.getName().equals("EditComputerDetails")) {
			data= new Object[1][4]; //[row][col]
			// row 1
			data[0][0] = "name1";
			data[0][1] = "2020-04-10";
			data[0][2] = "2020-12-10";
			data[0][3] = "IBM";
			
		}else if(m.getName().equals("EditdataAndSave")) {
			data= new Object[1][2]; //[row][col]
			// row 1
			data[0][0] = "name2";
			data[0][1] = "ASUS";		
	}else if(m.getName().equals("searchandopen")) {
		data= new Object[1][4];
		// row 1
		data[0][0] = "name1";
		data[0][1] = "2020-04-10";
		data[0][2] = "2020-12-10";
		data[0][3] = "IBM";		
}
		return data;
}
}