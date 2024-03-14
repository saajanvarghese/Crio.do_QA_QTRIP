package qtriptest.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import qtriptest.SeleniumWrapper;


public class HomePage {
    WebDriver driver;
    String url = "https://qtripdynamic-qa-frontend.vercel.app/";

    @FindBy(xpath = "//a[@class='nav-link login register']")
    private WebElement registerElement;

    @FindBy(xpath = "//input[@class='hero-input']")
    private WebElement searchCity;

    @FindBy(xpath = "//ul[@id='results']/a")
    private WebElement searchSuggestion;

    @FindBy(xpath = "//select[@id='category-select']")
    private WebElement category_dropdown_element;

    @FindBy(xpath = "//select[@id='duration-select']")
    private WebElement duration_dropdown_element;

    @FindBy(xpath = "//div[@onclick='clearCategory(event)']")
    private WebElement category_clear;

    @FindBy(xpath = "//div[@onclick='clearDuration(event)']")
    private WebElement duration_clear;

    public HomePage(WebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
        PageFactory.initElements(factory, this);
    }

    public void navigateToRegister() {
        String url = "https://qtripdynamic-qa-frontend.vercel.app/pages/register/";
        if(this.driver.getCurrentUrl() != url){
            this.driver.get(url);
        }
        registerElement.click();
    }

    public void navigateToHomePage() {
        String url = "https://qtripdynamic-qa-frontend.vercel.app/";
        if(this.driver.getCurrentUrl() != url){
            this.driver.get(url);
        }
    }


    public Boolean searchCity(String city) throws InterruptedException{

        // searchCity.click();
        // Thread.sleep(1000);
        // searchCity.sendKeys(city);
        // Thread.sleep(3000);
        SeleniumWrapper.clickAction(searchCity, driver);
         SeleniumWrapper.enterText(searchCity, city);

         //Thread.sleep(3000);

         WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='results']/a")));

        Assert.assertTrue(searchSuggestion.isDisplayed(), "Searched City is not Present");

        //searchSuggestion.click();
        SeleniumWrapper.clickAction(searchSuggestion, driver);

        Thread.sleep(1000);

        Assert.assertTrue(driver.getCurrentUrl().equals("https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/?" +"city=" + city.toLowerCase()), "Success");

        return true;

    }

    public Boolean check_filtered_results(String category_filter, String duration_filter, String expectedFilterResults) throws InterruptedException{

        Select category_dropdown = new Select(category_dropdown_element);
        Thread.sleep(2000);
        category_dropdown.selectByVisibleText(category_filter);

        Thread.sleep(3000);

        Select duration_dropdown = new Select(duration_dropdown_element);
        Thread.sleep(2000);
        duration_dropdown.selectByVisibleText(duration_filter);
 

        Thread.sleep(3000);

      List<WebElement> searchResults = driver.findElements(By.xpath("//div[@class='col-6 col-lg-3 mb-4']//a"));

      Thread.sleep(2000);

      String filtersearchResultsCount = String.valueOf(searchResults.size());

      Assert.assertEquals(filtersearchResultsCount, expectedFilterResults);

      Thread.sleep(5000);

      return true;
    }

    public Boolean check_unfiltered_results(String expectedUnfilterResults) throws InterruptedException{

      //category_clear.click();

      SeleniumWrapper.clickAction(category_clear, driver);

      Thread.sleep(5000); 

    //   duration_clear.click();
    SeleniumWrapper.clickAction(duration_clear, driver);

      Thread.sleep(5000);
      
      List<WebElement> searchResultafterclear = driver.findElements(By.xpath("//div[@class='col-6 col-lg-3 mb-4']//a"));
      String unfiltersearchResultsCount = String.valueOf(searchResultafterclear.size());

      Assert.assertEquals(unfiltersearchResultsCount, expectedUnfilterResults);

        return true;
    }

}