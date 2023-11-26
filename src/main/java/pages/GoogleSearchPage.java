package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class GoogleSearchPage {
    @FindBy(name = "q")
    WebElement searchBox;

    @FindBy(how = How.XPATH, using = "//a[contains(@href, 'wikipedia.org')]")
    List<WebElement> results;


    public GoogleSearchPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void searchFor(String text) {
        searchBox.sendKeys(text + Keys.ENTER);
    }


    public List<WebElement> getResults(WebDriverWait wait) {
        wait.until(ExpectedConditions.visibilityOfAllElements(results));
        return results;
    }

}
