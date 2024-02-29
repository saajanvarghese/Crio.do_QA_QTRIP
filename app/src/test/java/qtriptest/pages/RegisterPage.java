package qtriptest.pages;

import java.util.UUID;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;


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


    public Boolean registerNewUser(String username, String password, String confirmpassword, Boolean makeUserDynamic) throws InterruptedException {
       
        //driver.get("https://qtripdynamic-qa-frontend.vercel.app/pages/register/");

        if(makeUserDynamic) {
            emailAddress=username.split("@")[0]+UUID.randomUUID().toString()+"@"+username.split("@")[1];
        }else{
            emailAddress = username;
        }
            userNameInput.clear();
            userNameInput.sendKeys(emailAddress);
            passwordInput.clear();
            passwordInput.sendKeys(password);
            confirmPasswordInput.clear();
            // Thread.sleep(5000);
            confirmPasswordInput.sendKeys(confirmpassword);
            //Thread.sleep(5000);
            registerNowElement.click();
            Thread.sleep(3000);
        

        this.USER_EMAIL = emailAddress;
        return this.driver.getCurrentUrl().endsWith("/login");
    }
}
