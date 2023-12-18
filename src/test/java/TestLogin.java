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
    WebDriver driver;
    WebDriverWait wait;
    static String USERNAME = "Student-1";
    static String PASSWORD = "59c5266dc9";
    String groupName = "Group" + System.currentTimeMillis();


    @BeforeAll
    static void property() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
    }

    @BeforeEach
    void open() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");

        driver = new ChromeDriver(chromeOptions);
//        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    void testNewGroup(){
        login();
        WebElement addGroupIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-btn")));
        addGroupIcon.click();

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

        List<WebElement> groupTable =
                wait.until(ExpectedConditions
                        .visibilityOfAllElementsLocatedBy(By.cssSelector(".mdc-data-table__cell")));

        assertEquals(groupTable.get(1).getText(), groupName);


    }

    @AfterEach
    void close() {
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
                        .visibilityOfElementLocated(By.xpath("//*[@id=\"app\"]/main/nav/ul/li[3]/a")));
        assertEquals("Hello, Student-1", helloUser.getText());

    }
}
