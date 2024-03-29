package ru.bellintegrator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.GoogleSearchPage;
import java.util.List;

/**
 * Тестовый класс для проверки функциональности поиска на сайте банка "Открытие".
 * Использует Selenium WebDriver для взаимодействия с веб-страницей и JUnit для утверждений.
 */
public class OpenBankSearchTest extends BaseTest {

    private WebDriverWait wait;
    private GoogleSearchPage googleSearchPage;

    /**
     * Подготавливает окружение перед каждым тестом.
     * Инициализирует ожидание WebDriverWait и страницу поиска Google.
     */
    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        wait = new WebDriverWait(driver, 60);
        googleSearchPage = new GoogleSearchPage(driver, wait);
    }

    /**
     * Параметризованный тест для проверки функции поиска Google с запросом "Открытие".
     * Тест ищет ссылки в поисковой выдаче Google, переходит на сайт банка и проверяет курсы валют.
     *
     * @param query Строка поискового запроса.
     */
    @ParameterizedTest
    @ValueSource(strings = {"Открытие"})
    public void testOpenBankSearch(String query) {
        googleSearchPage.searchFor(query);
        googleSearchPage.waitForSearchResults();
        googleSearchPage.clickLinkWithText("//h3[contains(text(),'Банк Открытие: кредит наличными')]");

        // Обработка всплывающего окна с cookie
        try {
            WebElement cookieAcceptButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".CookieWarning_cookie-warning-button__XaO44")));
            cookieAcceptButton.click();
        } catch (Exception e) {
            System.out.println("Всплывающее окно с cookie не найдено.");
        }

        // Переход на страницу с курсами валют и проверка их наличия
        WebElement allRatesLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Все курсы")));
        allRatesLink.click();

        List<WebElement> currencyRows = driver.findElements(By.xpath("//section[contains(@class, 'currency-exchange') and not(contains(@style, 'display: none'))]//table[@class='card-rates-table cards']//tr[contains(@class, 'card-rates-table__row')]"));
        for (WebElement row : currencyRows) {
            System.out.println(row.getText());
        }

        Assertions.assertTrue(currencyRows.size() >= 3, "Количество валют меньше трех");

        verifyCurrencyRates("currency__icon--usd", "card-rates-table__sale", "card-rates-table__purchase", "Курс покупки долларов не меньше курса продажи");
        verifyCurrencyRates("currency__icon--eur", "card-rates-table__sale", "card-rates-table__purchase", "Курс покупки евро не меньше курса продажи");
    }

    /**
     * Проверяет курсы покупки и продажи валюты.
     * Утверждает, что курс покупки меньше курса продажи.
     *
     * @param currencyIconClass Класс иконки валюты на странице.
     * @param buyRateClass Класс элемента с курсом покупки.
     * @param sellRateClass Класс элемента с курсом продажи.
     * @param errorMessage Сообщение об ошибке, если условие не выполняется.
     */
    protected void verifyCurrencyRates(String currencyIconClass, String buyRateClass, String sellRateClass, String errorMessage) {
        WebElement buyRateElement = driver.findElement(By.xpath(String.format("//tr[contains(@class, 'card-rates-table__row') and descendant::div[contains(@class, '%s')]]/td[contains(@class, '%s')]", currencyIconClass, buyRateClass)));
        WebElement sellRateElement = driver.findElement(By.xpath(String.format("//tr[contains(@class, 'card-rates-table__row') and descendant::div[contains(@class, '%s')]]/td[contains(@class, '%s')]", currencyIconClass, sellRateClass)));

        double buyRate = parseCurrencyRate(buyRateElement.getText());
        double sellRate = parseCurrencyRate(sellRateElement.getText());

        Assertions.assertTrue(buyRate < sellRate, errorMessage);
    }

    /**
     * Преобразует строку с курсом валюты в числовой формат.
     *
     * @param rateText Текст, содержащий курс валюты.
     * @return Числовое значение курса валюты.
     */
    private double parseCurrencyRate(String rateText) {
        return Double.parseDouble(rateText.replace(",", "."));
    }
}
