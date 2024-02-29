package qtriptest;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.MalformedInputException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import io.github.bonigarcia.wdm.WebDriverManager;


public class DriverSingleton {


    WebDriver driver;

    private static DriverSingleton instanceOfSingleton;

    private DriverSingleton() throws MalformedURLException{

 ChromeOptions options = new ChromeOptions();
       


        driver = new ChromeDriver(options);
		WebDriverManager.chromedriver().setup();

        driver.manage().window().maximize();

        System.out.println("Create Driver");
    }

    public static DriverSingleton getInstanceOfSingletonBrowserClass() throws MalformedURLException{
       
        // null check
        if(instanceOfSingleton == null){

            instanceOfSingleton = new DriverSingleton();
        }
       
       
        return instanceOfSingleton;
        
    }

    public WebDriver getDriver(){

        return driver;
    }


}