package ru.bellintegrator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.GoogleSearchPage;

import java.util.List;

public class TableSearchTest extends BaseTest {

    private WebDriverWait wait;
    private GoogleSearchPage googleSearchPage;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp(); // Вызывает setUp из BaseTest
        wait = new WebDriverWait(driver, 60); // Инициализируется перед созданием GoogleSearchPage
        googleSearchPage = new GoogleSearchPage(driver, wait);
    }

    @ParameterizedTest
    @ValueSource(strings = {"таблица"}) // Примеры запросов для теста
    public void testOrderOfTeachers(String query) {

        googleSearchPage.searchFor(query);
        googleSearchPage.waitForSearchResults();
        googleSearchPage.clickLinkWithText("//div[contains(@class, 'g')]//a[contains(@href, 'wikipedia.org') and .//h3[contains(text(),'Таблица')]]");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("mw-content-text")));
        WebElement teachingStaffTable = driver.findElement(By.xpath("//h3[.//span[contains(text(),'Простая таблица')]]/following-sibling::table[1]"));

        List<WebElement> tableRows = teachingStaffTable.findElements(By.tagName("tr"));

        String firstRowText = tableRows.get(0).getText();
        String lastRowText = tableRows.get(tableRows.size() - 1).getText();

        Assertions.assertTrue((firstRowText.contains("Сергей Владимирович") && lastRowText.contains("Сергей Адамович")), "Порядок преподавателей неверный.");
    }

}
