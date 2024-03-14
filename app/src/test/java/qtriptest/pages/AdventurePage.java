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

        SeleniumWrapper.enterText(searchCity, cityName);  //Enter City Name in SearchCity TextBox

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='results']/a")));

        Assert.assertTrue(searchSuggestion.isDisplayed(), "Searched City is Present");

        SeleniumWrapper.clickAction(searchSuggestion, driver);  //Click Search Suggestions

        Thread.sleep(1000);

        return true;
    }

    public Boolean selectAdventure(String adVentureName) throws InterruptedException{


        SeleniumWrapper.clickAction(adventuretxtbox, driver); //Click Adventure TextBox
        SeleniumWrapper.enterText(adventuretxtbox, adVentureName); //Enter Text in Adventure TextBox

        Thread.sleep(3000);

        SeleniumWrapper.clickAction(adventureSearchResults, driver); //Click Adventure Search Results

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.id("adventure-name")), adVentureName));

        return true;
    }
    
}
