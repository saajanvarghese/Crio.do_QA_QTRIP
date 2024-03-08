package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.Logs;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testCase_01 {

    public static String lastGeneratedUserName;
    static WebDriver driver;

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

        System.out.println("Hashcode of driver is" + driver.hashCode());
        
        driver.get("https://qtripdynamic-qa-frontend.vercel.app/");
        driver.manage().window().maximize();

        logStatus("driver", "Initializing driver", "Success");   
    }

     @Test(description = "Verify user registration -login -logout", dataProvider = "data-provider", dataProviderClass = DP.class,  priority = 1, groups={"Login Flow"}, enabled = true)
    public static void TestCase01(String username, String password) throws InterruptedException {
        Boolean status;
        try{
            HomePage home = new HomePage(driver);
            home.navigateToRegister();
            Thread.sleep(2000);
            Assert.assertTrue(driver.getCurrentUrl().equals("https://qtripdynamic-qa-frontend.vercel.app/pages/register/"), "success");
            logStatus("Page Test", "navigation to Register Page", "Success");
            RegisterPage register = new RegisterPage(driver);

            status = register.registerNewUser("testuser@gmail.com", "Saj@123", "Saj@123", true);
            if(status){
                logStatus("Page Test", "User Registeration Successfully", "Success");
            }
            else{
                logStatus("Page Test", "User Registeration Failed", "Failure");
            }

            System.out.println(status);
            Assert.assertTrue(status, "User Registration Failed");
            
            lastGeneratedUserName = register.USER_EMAIL;
            LoginPage login = new LoginPage(driver);

           status =login.logInUser(lastGeneratedUserName, "Saj@123");
           if(status){
            logStatus("Page Test", "User Logged In Successfully", "Success");
           }
           else{
            logStatus("Page Test", "User Logged In Fail", "Fail");
           }
           Assert.assertTrue(status, "User Login Failed");

            status = login.logOutUser();
            if(status){
                logStatus("Page Test", "User Logged Out Successfully", "Success");
               }
               else{
                logStatus("Page Test", "User Logged Out Fail", "Fail");
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
        driver.close();
        driver.quit();
        logStatus("driver", "Quiting Driver", "Success");
    }
}

