package qtriptest.pages;

import java.time.Duration;
import java.util.UUID;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import qtriptest.SeleniumWrapper;


public class RegisterPage {

        WebDriver driver;
        @FindBy(name = "email")
        private WebElement userNameInput;
    
        @FindBy(name = "password")
        private WebElement passwordInput;
    
        @FindBy(xpath = "//input[@type='password' and @name='confirmpassword']")
        private WebElement confirmPasswordInput;
    
        @FindBy(xpath = "//button[@class='btn btn-primary btn-login']")
        private WebElement registerNowElement;
    
    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
        PageFactory.initElements(factory, this);
    }

    String emailAddress ="";

    public String USER_EMAIL = "";


    public Boolean registerNewUser(String username, String password, Boolean makeUserDynamic) throws InterruptedException {
       
        driver.get("https://qtripdynamic-qa-frontend.vercel.app/pages/register/");

        if(makeUserDynamic) {
            emailAddress=username.split("@")[0]+UUID.randomUUID().toString()+"@"+username.split("@")[1];
        }else{
            emailAddress = username;
        }

            SeleniumWrapper.enterText(userNameInput, emailAddress); //Enter Email in Email TextBox
            SeleniumWrapper.enterText(passwordInput, password); //Enter Password in Password TextBox
            SeleniumWrapper.enterText(confirmPasswordInput, password);  //Enter password in Confirm Password TextBox
            SeleniumWrapper.clickAction(registerNowElement, driver); //Click Register Button

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.urlToBe("https://qtripdynamic-qa-frontend.vercel.app/pages/login"));

        this.USER_EMAIL = emailAddress;
        return this.driver.getCurrentUrl().endsWith("/login");
    }
}
