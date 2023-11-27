package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

/**
 * Класс GoogleSearchPage предназначен для взаимодействия со страницей поиска Google в рамках автоматизированных тестов.
 * Этот класс обеспечивает методы для выполнения поисковых запросов, ожидания результатов и взаимодействия с ними.
 *
 * Для работы с элементами страницы используется Selenium PageFactory для упрощения работы с элементами DOM.
 */
public class GoogleSearchPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Элемент поисковой строки на странице Google
    @FindBy(name = "q")
    WebElement searchBox;

    // Список элементов результатов поиска, содержащих ссылки на Wikipedia
    @FindBy(how = How.XPATH, using = "//a[contains(@href, 'wikipedia.org')]")
    List<WebElement> results;

    /**
     * Конструктор для инициализации страницы с использованием WebDriver и WebDriverWait.
     *
     * @param driver WebDriver, используемый для взаимодействия с браузером.
     * @param wait WebDriverWait, используемый для управления временем ожидания элементов.
     */
    public GoogleSearchPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    /**
     * Альтернативный конструктор для инициализации страницы только с WebDriver.
     *
     * @param driver WebDriver, используемый для взаимодействия с браузером.
     */
    public GoogleSearchPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Выполняет поиск в Google с использованием указанного текста.
     *
     * @param text Текст для поиска.
     */
    public void searchFor(String text) {
        searchBox.sendKeys(text + Keys.ENTER);
    }

    /**
     * Ожидает появления результатов поиска на странице.
     */
    public void waitForSearchResults() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rso")));
    }

    /**
     * Кликает по ссылке, которая содержит указанный текст.
     *
     * @param linkText XPath текста ссылки для клика.
     */
    public void clickLinkWithText(String linkText) {
        WebElement link = driver.findElement((By.xpath(linkText)));
        link.click();
    }

    /**
     * Возвращает список результатов поиска после их появления на странице.
     *
     * @param wait WebDriverWait для управления временем ожидания элементов.
     * @return Список WebElement, представляющих результаты поиска.
     */
    public List<WebElement> getResults(WebDriverWait wait) {
        wait.until(ExpectedConditions.visibilityOfAllElements(results));
        return results;
    }

}

