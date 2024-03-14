package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.SeleniumWrapper;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class testCase_04 {
    public static String lastGeneratedUserName;
    static WebDriver driver;
    static ExtentReports reports;
    static ExtentTest test;

    public static void logStatus(String type, String message, String status) {

        System.out.println(String.format("%s |  %s  |  %s | %s", String.valueOf(java.time.LocalDateTime.now()), type,
                message, status));
    }

    //Initialize webdriver for our unit tests
    @BeforeTest(alwaysRun = true)
    public static void createDriver() throws MalformedURLException {
        logStatus("driver", "Initializing driver", "Started");
        // Launch Browser using Zalenium
        DriverSingleton singleton = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = singleton.getDriver();

        System.out.println("Hashcode of driver is" + driver.hashCode());

        ReportSingleton rpt = ReportSingleton.getInstanceOfSingleTonReportClass();
        reports = rpt.getReport();

        driver.get("https://qtripdynamic-qa-frontend.vercel.app/");
        driver.manage().window().maximize();

        test = reports.startTest("TestCase 02", "Start TestCase 02");

        logStatus("driver", "Initializing driver", "Success");   
    }

  @Test(description = "Booking adventure and validate Reservation Table", dataProvider = "data-provider", dataProviderClass = DP.class,  priority = 4, groups={"Reliability Flow"}, enabled = true)
    public static void TestCase04(String username,String password,String set1,String set2,String set3) throws InterruptedException, MalformedURLException
    {
        String[] dataset1=set1.split(";");
        String[] dataset2=set2.split(";");
        String[] dataset3=set3.split(";");
       List<String[]> data=new ArrayList<>();
       data.add(dataset1);
       data.add(dataset2);
       data.add(dataset3);
       System.out.println(data);

       Boolean status;
       try{
       HomePage home = new HomePage(driver);
            home.navigateToRegister();
            Thread.sleep(2000);
            Assert.assertTrue(driver.getCurrentUrl().equals("https://qtripdynamic-qa-frontend.vercel.app/pages/register/"), "success");
            logStatus("Page Test", "navigation to Register Page", "Success");
            RegisterPage register = new RegisterPage(driver);

            status = register.registerNewUser(username, password, true);
            if(status){
                logStatus("Page Test", "User Registeration Successfully", "Success");
                test.log(LogStatus.PASS, "User Registered Successful");
                test.log(LogStatus.PASS, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) + "Navigation to RegisterPage is Successful");
            }
            else{
                logStatus("Page Test", "User Registeration Failed", "Failure");
                test.log(LogStatus.FAIL, "User Registered Unsuccessful");
                test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) + "Navigation to RegisterPage is unsuccessful");
            }
            Assert.assertTrue(status, "User Registration Failed");
            
            lastGeneratedUserName = register.USER_EMAIL;
            LoginPage login = new LoginPage(driver);

           status =login.logInUser(lastGeneratedUserName, password);
           if(status){
            logStatus("Page Test", "User Logged In Successfully", "Success");
            test.log(LogStatus.PASS, "User Logged In : Success");
            test.log(LogStatus.PASS, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) + "User Logged In : Success");
           }
           else{
            logStatus("Page Test", "User Logged In Fail", "Fail");
            test.log(LogStatus.FAIL, "User LogIn : Fail");
            test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) + "User LogIn : Fail");
           }
           Assert.assertTrue(status, "User Login Failed");


        HistoryPage history=new HistoryPage(driver);
        for(String[] list:data)
        {
        home.searchCity(list[0]);

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

        Assert.assertTrue(driver.getCurrentUrl().equals("https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/?" +"city=" + list[0].toLowerCase()), "Success");


        AdventurePage adventurepage=new AdventurePage(driver);
        System.out.println(list[1]);
        adventurepage.selectAdventure(list[1]);

        System.out.println(list[1]);
                if(status){
                logStatus("Page Test", "Success : Adventure Page Exists", "Success");
                test.log(LogStatus.PASS, "Success : Adventure Page Exists");
                test.log(LogStatus.PASS, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) + "Success : Adventure Page Exists");
               }
               else{
                logStatus("Page Test", "Fail : Adventure Page Does not Exists", "Fail");
                test.log(LogStatus.FAIL, "Fail : Adventure Page Does not Exists");
                test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) + "Fail : Adventure Page Does not Exists");
               }
               WebElement adventurePageCheck = driver.findElement(By.id("adventure-name"));
               String adventureCheck = adventurePageCheck.getText();
               Assert.assertTrue(adventureCheck.contains(list[1]), "Fail : Adventure Page Does not Exists");

               Thread.sleep(3000);
                       
        AdventureDetailsPage advPage=new AdventureDetailsPage(driver);
        advPage.bookAdventure(list[2], list[3], list[4]);

        if(status){
            logStatus("Page Test", "Adventure Booked Successfully", "Success");
            test.log(LogStatus.PASS, "Adventure Booked Successfully");
                test.log(LogStatus.PASS, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) + "Adventure Booked Successfully");
           }
           else{
            logStatus("Page Test", "Adventure Booked Failed", "Fail");
            test.log(LogStatus.FAIL, "Adventure Booked Failed");
                test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) + "Adventure Booked Failed");
           }

           Assert.assertTrue(status, "Adventure Booking Failed");

           //navigate to HomePage
           home.navigateToHomePage();
        }
        
        // navigate to history page
        history.navigateToHistoryPage();

        Thread.sleep(3000);

        Assert.assertTrue(driver.getCurrentUrl().contains("https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/reservations/index.html"));

       status = history.bookingHistoryCheck();
       if(status){
        logStatus("Page Test", "Success: Bookings are Present", "Success");
        test.log(LogStatus.PASS, "Adventure Booked Successfully");
        test.log(LogStatus.PASS, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) + "Adventure Booked Successfully");
       }
       else{
        logStatus("Page Test", "Fail: Bookings are not Present", "Fail");
        test.log(LogStatus.FAIL, "Adventure Booked Failed");
        test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) + "Adventure Booked Failed");
       }
       Assert.assertTrue(status, "Fail: Bookings are not Present");


       status = login.logOutUser();
       if(status){
           logStatus("Page Test", "User Logged Out Successfully", "Success");
           test.log(LogStatus.PASS, "User LogOut : Success");
           test.log(LogStatus.PASS, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) + "User LogOut : Success");
          }
          else{
           logStatus("Page Test", "User Logged Out Fail", "Fail");
           test.log(LogStatus.FAIL, "User Logged Out Fail");
           test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)) + "User Logged Out Fail");
          }
          Assert.assertTrue(status, "User Login Out Failed");
    } 
    catch(Exception e){
        logStatus("Page Test", "TestCase 04 Validation", "Failed");
        e.printStackTrace();
    } 
}

        @AfterTest(enabled = true)
        public static void quitDriver() throws MalformedURLException {
            reports.endTest(test);
            reports.flush();

        driver.quit();
        logStatus("driver", "Quiting Driver", "Success");
    }
}
