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
 * Класс TableSearchTest предназначен для автоматизированного тестирования порядка данных в таблице преподавателей в статье Википедии про таблицы,
 * полученной через поиск в Google.
 *
 * Этот класс наследуется от BaseTest, который содержит общую конфигурацию для всех тестов.
 * В каждом тесте выполняется поиск в Google с заданным запросом, и проверяется порядок элементов в полученной таблице.
 */
public class TableSearchTest extends BaseTest {

    private WebDriverWait wait;
    private GoogleSearchPage googleSearchPage;

    /**
     * Подготавливает окружение перед каждым тестом.
     * Инициализирует экземпляры WebDriverWait и GoogleSearchPage для последующего использования в тестах.
     */
    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        wait = new WebDriverWait(driver, 60);
        googleSearchPage = new GoogleSearchPage(driver, wait);
    }

    /**
     * Параметризованный тест для проверки порядка данных в таблице преподавателей.
     *
     * @param query строка запроса для поиска в Google.
     *              В данном случае, используется слово "таблица" для поиска.
     */
    @ParameterizedTest
    @ValueSource(strings = {"таблица"})
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
