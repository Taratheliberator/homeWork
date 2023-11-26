package ru.bellintegrator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.GoogleSearchPage;

import java.util.List;

public class OpenBankSearchTest extends BaseTest {

    private WebDriverWait wait;
    private GoogleSearchPage googleSearchPage;
    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        googleSearchPage = new GoogleSearchPage(driver);
        wait = new WebDriverWait(driver, 60);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Открытие"})
    public void testOpenBankSearch(String query) {

        googleSearchPage.searchFor(query);

        WebElement linkToOpen = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h3[contains(text(),'Банк Открытие: кредит наличными')]")));
        linkToOpen.click();

        try {
            WebElement cookieAcceptButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".CookieWarning_cookie-warning-button__XaO44")));
            cookieAcceptButton.click();
        } catch (Exception e) {
            System.out.println("Всплывающее окно с cookie не найдено.");
        }

        WebElement allRatesLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Все курсы")));
        allRatesLink.click();

        List<WebElement> currencyRows = driver.findElements(By.xpath("//section[contains(@class, 'currency-exchange') and not(contains(@style, 'display: none'))]//table[@class='card-rates-table cards']//tr[contains(@class, 'card-rates-table__row')]"));

        for (WebElement row : currencyRows) {
            System.out.println(row.getText());
        }

        Assertions.assertTrue(currencyRows.size() >= 3, "Количество валют меньше трех");

        WebElement usdBuyRateElement = driver.findElement(By.xpath("//tr[contains(@class, 'card-rates-table__row') and descendant::div[contains(@class, 'currency__icon--usd')]]/td[contains(@class, 'card-rates-table__sale')]"));
        WebElement usdSellRateElement = driver.findElement(By.xpath("//tr[contains(@class, 'card-rates-table__row') and descendant::div[contains(@class, 'currency__icon--usd')]]/td[contains(@class, 'card-rates-table__purchase')]"));
        double usdBuyRate = Double.parseDouble(usdBuyRateElement.getText());
        double usdSellRate = Double.parseDouble(usdSellRateElement.getText());
        Assertions.assertTrue(usdBuyRate < usdSellRate, "Курс покупки долларов не меньше курса продажи");

        WebElement eurBuyRateElement = driver.findElement(By.xpath("//tr[contains(@class, 'card-rates-table__row') and descendant::div[contains(@class, 'currency__icon--eur')]]/td[contains(@class, 'card-rates-table__sale')]"));
        WebElement eurSellRateElement = driver.findElement(By.xpath("//tr[contains(@class, 'card-rates-table__row') and descendant::div[contains(@class, 'currency__icon--eur')]]/td[contains(@class, 'card-rates-table__purchase')]"));
        double eurBuyRate = Double.parseDouble(eurBuyRateElement.getText().replace(",", "."));
        double eurSellRate = Double.parseDouble(eurSellRateElement.getText().replace(",", "."));
        Assertions.assertTrue(eurBuyRate < eurSellRate, "Курс покупки евро не меньше курса продажи");
    }

}