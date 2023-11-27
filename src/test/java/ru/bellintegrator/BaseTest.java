package ru.bellintegrator;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.GoogleSearchPage;

public abstract class BaseTest {
    protected WebDriver driver;



    protected WebDriverWait wait;
    @BeforeEach
    public void setUp() {
     //   System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 60);
        driver.get("https://www.google.com");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

