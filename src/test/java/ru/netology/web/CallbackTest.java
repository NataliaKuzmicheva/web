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

        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldSendForm() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79000000000");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        WebElement resultElement = driver.findElement(By.cssSelector("[data-test-id='order-success']"));
        assertTrue(resultElement.isDisplayed());
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", resultElement.getText().trim());
    }

    @Test
    void shouldSendFormWithDoubleName() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Петров-Иванов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79000000000");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        WebElement resultElement = driver.findElement(By.cssSelector("[data-test-id='order-success']"));
        assertTrue(resultElement.isDisplayed());
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", resultElement.getText().trim());
    }

    @Test
    void shouldNotSendFormWithoutCheckbox() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79000000000");
        driver.findElement(By.cssSelector("button")).click();
        WebElement resultElement = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid .checkbox__text"));
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", resultElement.getText().trim());
    }

    @Test
    void shouldNotSendFormWithEnglishLanguage() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Ivanov Ivan");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79000000000");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        WebElement resultElement = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", resultElement.getText().trim());
    }

    @Test
    void shouldNotSendFormWithoutName() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79000000000");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        WebElement resultElement = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub "));
        assertEquals("Поле обязательно для заполнения", resultElement.getText().trim());
    }

    @Test
    void shouldNotSendFormWithEightInNumberPhone() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("89000000000");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        WebElement resultElement = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub "));
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", resultElement.getText().trim());
    }

    @Test
    void shouldNotSendFormWithoutPhone() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        WebElement resultElement = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub "));
        assertEquals("Поле обязательно для заполнения", resultElement.getText().trim());
    }

}


