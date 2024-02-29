package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testCase_03 {
    public static String lastGeneratedUserName;
    static WebDriver driver;

    public static void logStatus(String type, String message, String status) {

        System.out.println(String.format("%s |  %s  |  %s | %s", String.valueOf(java.time.LocalDateTime.now()), type,
                message, status));
    }

    // Initialize webdriver for our unit tests
    @BeforeTest
    public static void createDriver() throws MalformedURLException, InterruptedException {
        logStatus("driver", "Initializing driver", "Started");
        // Launch Browser using Zalenium
        DriverSingleton singleton = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = singleton.getDriver();

        System.out.println("Hashcode of driver is" + driver.hashCode());

        driver.get("https://qtripdynamic-qa-frontend.vercel.app/");
        Thread.sleep(3000);

        logStatus("driver", "Initializing driver", "Success");     
    }
    @Test(description = "Verify Reserving an adventure functionality", dataProvider = "data-provider", dataProviderClass = DP.class,  priority = 3, groups={"Booking and Cancellation Flow"}, enabled = true)
    public static void TestCase03(String userName, String password, String cityName, String adVentureName,String guestName, String date, String count) throws InterruptedException {
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
            lastGeneratedUserName = register.USER_EMAIL;
            LoginPage login = new LoginPage(driver);

            login.logInUser(lastGeneratedUserName, "Saj@123");

            Thread.sleep(2000);

            AdventurePage adventurepage = new AdventurePage(driver);

            adventurepage.searchCity(cityName);

            Assert.assertTrue(driver.getCurrentUrl().equals("https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/?" +"city=" + cityName.toLowerCase()), "Success");

            adventurepage.selectAdventure(adVentureName);

            AdventureDetailsPage adventuredetails = new AdventureDetailsPage(driver);

            adventuredetails.bookAdventure(guestName, date, count);
            
            HistoryPage historypage = new HistoryPage(driver);

            historypage.navigateToHistoryPage();

            Thread.sleep(5000);

            historypage.transactiondetails();

            logStatus("Page Test", "User Logged In Successfully", "Success");
            login.logOutUser();
            logStatus("Page Test", "User Logged Out Successfully", "Success");
        }
        catch(Exception e){
            logStatus("Page Test", "TestCase 03 Validation", "Failed");
            e.printStackTrace();
        }            
        }

        @AfterTest(enabled = false)
        public static void quitDriver() throws MalformedURLException {
        driver.close();
        driver.quit();
        logStatus("driver", "Quiting Driver", "Success");
    }
}
