package ru.bellintegrator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebElement;
import pages.GoogleSearchPage;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class GladiolusSearchTest extends BaseTest {
    private GoogleSearchPage googleSearchPage;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        googleSearchPage = new GoogleSearchPage(driver);
    }

    @Test
    public void testGooglePageTitle() {

        String pageTitle = driver.getTitle();
        System.out.println("Заголовок страницы: " + pageTitle);
        assertEquals("Google", driver.getTitle(), "Заголовок должен быть Google");
    }

    @ParameterizedTest
    @ValueSource(strings = {"гладиолус"})
    public void testGoogleSearch(String query) {
        googleSearchPage.searchFor(query);

        List<WebElement> wikipediaLinks = googleSearchPage.getResults(wait);
        for (WebElement link : wikipediaLinks) {
            String href = link.getAttribute("href");
            System.out.println(href);
        }

        assertFalse(wikipediaLinks.isEmpty(), "Ссылка на " + query + " в Википедии не найдена");
    }
}
