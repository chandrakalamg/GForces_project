package com.projects.util;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestNGListener implements ITestListener {
	
	public void onTestFailure(ITestResult result) {
		System.out.println("Test Failed:"+result.getName());
		System.out.println(result.getStatus());
		System.out.println(result.getThrowable().getMessage());
	}
	
	public void onTestSuccess(ITestResult result) {
		System.out.println("Test Passed: "+result.getName());
	}
}
