import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLogin {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String USERNAME = "Student-1";
    private static final String PASSWORD = "59c5266dc9";



    @BeforeAll
    public static void property() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
    }

    @BeforeEach
    public void open() {
        // чтобы окно не открывалось
//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("--headless");
//        driver = new ChromeDriver(chromeOptions);


        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void testNewGroup(){
        login();
        WebElement addGroupIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-btn")));
        addGroupIcon.click();

        String groupName = "Group" + System.currentTimeMillis();
        WebElement addGroupField =
                wait.until(ExpectedConditions
                        .visibilityOfElementLocated(By.cssSelector("form#update-item input[type='text']")));
        addGroupField.sendKeys(groupName);

        WebElement addGroupButton = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("form#update-item button")));
        addGroupButton.click();

        WebElement closeButton = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .xpath("//*[@id='app']/main/div/div/div[2]/div[2]/div/div[1]/button")));
        closeButton.click();

        WebElement groupTable =
                wait.until(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//td[text()='%s']".formatted(groupName))));

        assertEquals(groupTable.getText(), groupName);

    }

    @AfterEach
    public void close() {
        driver.quit();
    }

    private void login() {
        driver.get("https://test-stand.gb.ru/login");
        WebElement userName =
                wait.until(ExpectedConditions
                        .visibilityOfElementLocated(By.cssSelector("form#login input[type='text']")));
        WebElement userPassword =
                wait.until(ExpectedConditions
                        .visibilityOfElementLocated(By.cssSelector("form#login input[type='password']")));
        WebElement buttonSubmit =
                wait.until(ExpectedConditions
                        .visibilityOfElementLocated(By.cssSelector("form#login button")));

        userName.sendKeys(USERNAME);
        userPassword.sendKeys(PASSWORD);
        buttonSubmit.click();
        wait.until(ExpectedConditions.invisibilityOf(buttonSubmit));

        WebElement helloUser =
                wait.until(ExpectedConditions
                        .visibilityOfElementLocated(By.cssSelector("nav li.mdc-menu-surface--anchor a")));
        assertEquals("Hello, %s".formatted(USERNAME), helloUser.getText());

    }
}
