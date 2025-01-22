package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CallbackTest {
    private WebDriver driver;

    @BeforeAll
    public static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldSendForm() {
        driver.get("http://localhost:9999");

        WebElement formElement = driver.findElement(By.cssSelector("form"));
        List<WebElement> inputs = formElement.findElements(By.cssSelector("input"));
        inputs.get(0).sendKeys("Иванов Иван");
        inputs.get(1).sendKeys("+79000000000");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        WebElement resultElement = driver.findElement(By.cssSelector("[data-test-id='order-success']"));
        assertTrue(resultElement.isDisplayed());
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", resultElement.getText().trim());
    }

    @Test
    void shouldSendFormWithDoubleName() {
        driver.get("http://localhost:9999");
        WebElement formElement = driver.findElement(By.cssSelector("form"));
        List<WebElement> inputs = formElement.findElements(By.cssSelector("input"));
        inputs.get(0).sendKeys("Иванова Анна-Мария");
        inputs.get(1).sendKeys("+79000000000");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
    }

    @Test
    void shouldNotSendFormWithEnglishLanguage() {
        driver.get("http://localhost:9999");
        WebElement formElement = driver.findElement(By.cssSelector("form"));
        List<WebElement> inputs = formElement.findElements(By.cssSelector("input"));
        inputs.get(0).sendKeys("Ivanov Ivan");
        inputs.get(1).sendKeys("+79000000000");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
    }

    @Test
    void shouldNotSendFormWithEightInNumberPhone() {
        driver.get("http://localhost:9999");
        WebElement formElement = driver.findElement(By.cssSelector("fform"));
        List<WebElement> inputs = formElement.findElements(By.cssSelector("input"));
        inputs.get(0).sendKeys("Иванов Иван");
        inputs.get(1).sendKeys("89000000000");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
    }

}


