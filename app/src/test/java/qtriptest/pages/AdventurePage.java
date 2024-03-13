package qtriptest.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import qtriptest.SeleniumWrapper;

public class AdventurePage {

    WebDriver driver;

     @FindBy(xpath = "//input[@class='hero-input']")
    private WebElement searchCity;

    @FindBy(xpath = "//ul[@id='results']//a")
    private WebElement searchSuggestion;

    @FindBy(xpath = "//input[@id='search-adventures']")
    private WebElement adventuretxtbox;

    @FindBy(xpath = "//div[@class='activity-card']//img")
    private WebElement adventureSearchResults;
    
    public AdventurePage(WebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
        PageFactory.initElements(factory, this);
    }

    public Boolean searchCity(String cityName) throws InterruptedException{

        // searchCity.click();
        // Thread.sleep(1000);
        // searchCity.sendKeys(cityName);
        // Thread.sleep(2000);
        SeleniumWrapper.enterText(searchCity, cityName);
        Thread.sleep(2000);
        Assert.assertTrue(searchSuggestion.isDisplayed(), "Searched City is Present");

        SeleniumWrapper.clickAction(searchSuggestion, driver);
        //searchSuggestion.click();

        Thread.sleep(1000);

        return true;
    }

    public Boolean selectAdventure(String adVentureName) throws InterruptedException{

        // adventuretxtbox.click();
        SeleniumWrapper.clickAction(adventuretxtbox, driver);
        SeleniumWrapper.enterText(adventuretxtbox, adVentureName);
        // adventuretxtbox.sendKeys(adVentureName);



        Thread.sleep(3000);

        //adventureSearchResults.click();
        SeleniumWrapper.clickAction(adventureSearchResults, driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.id("adventure-name")), adVentureName));

        return true;
    }
    
}
