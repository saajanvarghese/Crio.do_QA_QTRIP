package qtriptest.pages;

import java.util.UUID;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.Assert;


public class LoginPage {

    WebDriver driver;

    @FindBy(name = "email")
    private WebElement emailInput;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@class='btn btn-primary btn-login']")
    private WebElement login;

    @FindBy(xpath = "//a[@class='nav-link login']")
    private WebElement login_btn;

    @FindBy(xpath = "//div[@class='nav-link login register']")
    private WebElement logout;

    // @FindBy(xpath = "//div[@onclick='Logout()'']")
    // private WebElement logoutbtn;

    public LoginPage(WebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 30);
        PageFactory.initElements(factory, this);
    }

    public Boolean logInUser(String emailAddress, String password) throws InterruptedException{
        emailInput.clear();
        emailInput.sendKeys(emailAddress);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        login.click();
        Thread.sleep(5000);
        Assert.assertTrue(logout.isDisplayed(), "User Logged In");
        //Assert.assertTrue(logoutbtn.isDisplayed(), "Logout Button is Displayed");
        return true;
    }

    public void logOutUser() throws InterruptedException{

        logout.click();
        Thread.sleep(2000);
        Assert.assertTrue(login_btn.isDisplayed(), "User Logged Out");
    }
}
