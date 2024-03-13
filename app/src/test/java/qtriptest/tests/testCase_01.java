package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.SeleniumWrapper;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
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

public class testCase_01 {

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
    public static void createDriver() throws MalformedURLException {
        logStatus("driver", "Initializing driver", "Started");

        DriverSingleton singleton = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = singleton.getDriver();

        ReportSingleton rpt = ReportSingleton.getInstanceOfSingleTonReportClass();
        reports = rpt.getReport();

        System.out.println("Hashcode of driver is" + driver.hashCode());
        
        driver.get("https://qtripdynamic-qa-frontend.vercel.app/");
        driver.manage().window().maximize();

        test = reports.startTest("Your Test Name", "Description of your test");

        logStatus("driver", "Initializing driver", "Success");   
    }

     @Test(description = "Verify user registration -login -logout", dataProvider = "data-provider", dataProviderClass = DP.class,  priority = 1, groups={"Login Flow"}, enabled = true)
    public static void TestCase01(String username, String password) throws InterruptedException {
        Boolean status;
        try{
            HomePage home = new HomePage(driver);
            System.err.println(driver);
            home.navigateToRegister();
            Thread.sleep(2000);
            Assert.assertTrue(driver.getCurrentUrl().equals("https://qtripdynamic-qa-frontend.vercel.app/pages/register/"), "success");
            logStatus("Page Test", "navigation to Register Page", "Success");
            RegisterPage register = new RegisterPage(driver);

            status = register.registerNewUser(username, password, true);
            if(status){
                logStatus("Page Test", "User Registeration Successfully", "Success");
                test.log(LogStatus.PASS, "User Registered Successfull");
                test.log(LogStatus.PASS, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) + "Navigation to RegisterPage is Successful");
            }
            else{
                logStatus("Page Test", "User Registeration Failed", "Failure");
                test.log(LogStatus.FAIL, "User Registered Unsuccessfull");
                test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) + "Navigation to RegisterPage is unsuccessful");
            }
            Assert.assertTrue(status, "User Registration Failed");
            
            lastGeneratedUserName = register.USER_EMAIL;
            LoginPage login = new LoginPage(driver);

           status =login.logInUser(lastGeneratedUserName, password);
           if(status){
            logStatus("Page Test", "User Logged In Successfully", "Success");
            test.log(LogStatus.PASS, "User Login Successfull");
            test.log(LogStatus.PASS, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) + "Navigation to LoginPage: Success");
           }
           else{
            logStatus("Page Test", "User LogIn Fail", "Fail");
            test.log(LogStatus.FAIL, "User Login Unsuccessful");
            test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) + "Navigation to LoginPage : Unsuccessful");
           }
           Assert.assertTrue(status, "User Login Failed");

            status = login.logOutUser();
            if(status){
                logStatus("Page Test", "User Log Out Successfully", "Success");
                test.log(LogStatus.PASS, "User LogOut Successfull");
                test.log(LogStatus.PASS, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) + "User LogOut: Success");
               }
               else{
                logStatus("Page Test", "User Logged Out Fail", "Fail");
                test.log(LogStatus.FAIL, "User Logged Out Fail");
                test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) + "User Logged Out Fail");
               }
               Assert.assertTrue(status, "User Login Out Failed");
        }
        catch(Exception e){
            logStatus("Page Test", "TestCase 01 Validation", "Failed");
            e.printStackTrace();
        }            
        }

        @AfterSuite(enabled = false)
        public static void quitDriver() throws MalformedURLException {
            reports.endTest(test);
            reports.flush();

        driver.close();
        driver.quit();
        logStatus("driver", "Quiting Driver", "Success");
    }
}

