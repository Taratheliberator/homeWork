package ru.bellintegrator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Абстрактный класс BaseTest предназначен для обеспечения общих настроек тестовых сред для автоматизированных тестов.
 * В этом классе определяются основные настройки и ресурсы, необходимые для всех тестов, такие как инициализация WebDriver и WebDriverWait.
 *
 * Класс использует ChromeDriver для взаимодействия с браузером Chrome.
 * Этот класс предназначен для наследования другими тестовыми классами, которые требуют этих базовых настроек.
 */
public abstract class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    /**
     * Настройка тестовой среды перед каждым тестом.
     * Этот метод инициализирует экземпляр WebDriver и настраивает его, включая максимизацию окна браузера и установку времени ожидания.
     * Также происходит переход на начальную страницу Google.
     */
    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 60);
        driver.get("https://www.google.com");
    }

    /**
     * Очистка ресурсов после выполнения каждого теста.
     * Этот метод закрывает браузер и освобождает все используемые ресурсы, связанные с WebDriver.
     */
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
