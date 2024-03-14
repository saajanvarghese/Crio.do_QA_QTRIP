package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.SeleniumWrapper;
import qtriptest.pages.HomePage;
import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class testCase_02 {

    public static String lastGeneratedUserName;
    static WebDriver driver;
    static ExtentReports reports;
    static ExtentTest test;

    public static void logStatus(String type, String message, String status) {

        System.out.println(String.format("%s |  %s  |  %s | %s", String.valueOf(java.time.LocalDateTime.now()), type,
                message, status));
    }

   // Initialize webdriver for our unit tests
    @BeforeSuite(alwaysRun = true)
    public static void createDriver() throws MalformedURLException, InterruptedException {
        logStatus("driver", "Initializing driver", "Started");

        DriverSingleton singleton = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = singleton.getDriver();

        ReportSingleton rpt = ReportSingleton.getInstanceOfSingleTonReportClass();
        reports = rpt.getReport();

        System.out.println("Hashcode of driver is" + driver.hashCode());

        driver.get("https://qtripdynamic-qa-frontend.vercel.app/");
        driver.manage().window().maximize();
        
        test = reports.startTest("TestCase 02", "Start TestCase 02");

        logStatus("driver", "Initializing driver", "Success");     
    }
    
   @Test(description = "Verify the functionality of search filters", dataProvider = "data-provider", dataProviderClass = DP.class,  priority = 2, groups={"Search and Filter flow"}, enabled = true)
    public static void TestCase02(String city, String category_filter, String duration_filter, String expectedFilterResults, String expectedUnfilterResults) throws InterruptedException {

        Boolean status;
        try {
            logStatus("Page Test", "TestCase02 Validation", "Started");
            HomePage home = new HomePage(driver);

            home.navigateToHomePage();

           status = home.searchCity(city);

           if(status){
            logStatus("Page Test", "Search city Functionality Successful", "Success");
            test.log(LogStatus.PASS, "Search city Functionality Successful");
            test.log(LogStatus.PASS, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) + "Search city Functionality Successful");
           }
           else{
            logStatus("Page Test", "Search city Functionality Failed", "Fail");
            test.log(LogStatus.FAIL, "Search city Functionality Failed");
            test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) + "Search city Functionality Failed");
           }

           Assert.assertTrue(driver.getCurrentUrl().equals("https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/?" +"city=" + city.toLowerCase()), "Success");

          status = home.check_filtered_results(category_filter, duration_filter, expectedFilterResults);
          if(status){
            logStatus("Page Test", "Check Filtered Results Successful", "Success");
            test.log(LogStatus.PASS, "Check Filtered Results Successful");
            test.log(LogStatus.PASS, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) + "Check Filtered Results Successful");
           }
           else{
            logStatus("Page Test", "Check Filtered Results Failed", "Fail");
            test.log(LogStatus.FAIL, "Check Filtered Results Failed");
            test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) + "Check Filtered Results Failed");
           }
           Assert.assertTrue(status, "Check Filtered Results Failed");

           status = home.check_unfiltered_results(expectedUnfilterResults);
           if(status){
            logStatus("Page Test", "Check UnFiltered Results Successful", "Success");
            test.log(LogStatus.FAIL, "User Registered Unsuccessful");
            test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) + "Navigation to RegisterPage is unsuccessful");
           }
           else{
            logStatus("Page Test", "Check UnFiltered Results Failed", "Fail");
            test.log(LogStatus.FAIL, "Check UnFiltered Results Failed");
            test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) + "Check UnFiltered Results Failed");
           }
           Assert.assertTrue(status, "Check Filtered Results Failed");

        } catch (Exception e) {
            //TODO: handle exception
            logStatus("Page Test", "Test 2 Validation", "failed");
            e.printStackTrace();
        }

    }

    @AfterSuite(enabled = true)
        public static void quitDriver() throws MalformedURLException {
            reports.endTest(test);
            reports.flush();

        driver.quit();
        logStatus("driver", "Quiting Driver", "Success");
    }

}
