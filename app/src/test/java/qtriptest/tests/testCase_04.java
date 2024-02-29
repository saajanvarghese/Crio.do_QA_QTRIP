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
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testCase_04 {
    public static String lastGeneratedUserName;
    static WebDriver driver;

    public static void logStatus(String type, String message, String status) {

        System.out.println(String.format("%s |  %s  |  %s | %s", String.valueOf(java.time.LocalDateTime.now()), type,
                message, status));
    }

    // Initialize webdriver for our unit tests
    @BeforeTest
    public static void createDriver() throws MalformedURLException {
        logStatus("driver", "Initializing driver", "Started");
        // Launch Browser using Zalenium
        DriverSingleton singleton = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = singleton.getDriver();

        System.out.println("Hashcode of driver is" + driver.hashCode());

        driver.get("https://qtripdynamic-qa-frontend.vercel.app/");
        driver.manage().window().maximize();

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

       logStatus("Page Test", "User Logged In Successfully", "Success");
        HistoryPage history=new HistoryPage(driver);
        for(String[] list:data)
        {
        home.search_city(list[0]);
       // home.selectCity(list[0]);
        AdventurePage adventurepage=new AdventurePage(driver);
        adventurepage.selectAdventure(list[1]);
        
    //    Assert.assertTrue(driver.findElement(By.xpath("//h1[@id='adventure-name']")).getText().equals(list[1]));
    //     logStatus("page", "Select given adventure", "success");
        AdventureDetailsPage advPage=new AdventureDetailsPage(driver);
        advPage.bookAdventure(list[2], list[3], list[4]);
        // Assert.assertTrue(advPage.isBookingSuccessful());
        logStatus("page", "Book adventure", "success");
        
        // Assert.assertTrue(history.getReservation(list[2]));
        // logStatus("page", "Get reservation", "success");
        driver.get("https://qtripdynamic-qa-frontend.vercel.app/"); // to Start another Booking
        }

        // navigate to history page
        history.navigateToHistoryPage();

        Thread.sleep(3000);

        history.bookingHistoryCheck();


        logStatus("Page Test", "User Logged In Successfully", "Success");
        login.logOutUser();
        logStatus("Page Test", "User Logged Out Successfully", "Success");
    }


        @AfterTest(enabled = true)
        public static void quitDriver() throws MalformedURLException {
        driver.close();
        driver.quit();
        logStatus("driver", "Quiting Driver", "Success");
    }
}
