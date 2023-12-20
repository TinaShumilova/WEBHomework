import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLogin {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String USERNAME = "Student-1";
    private static final String PASSWORD = "59c5266dc9";

    private static String baseUrl = "https://test-stand.gb.ru/login";

    @BeforeAll
    public static void property() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
    }

    @BeforeEach
    public void open() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void testRow() {
        String groupName = "Group" + System.currentTimeMillis();

        MainPage mainPage = addNewGroup(groupName);
        assertEquals("active", mainPage.getStatusRow(groupName));

        mainPage.deleteRow(groupName);
        assertEquals("inactive", mainPage.getStatusRow(groupName));

        mainPage.restoreRow(groupName);
        assertEquals("active", mainPage.getStatusRow(groupName));

    }

    @Test
    public void errorLogin(){
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.errorLogin();
        assertEquals("401",loginPage.errorCode());
        assertEquals("Invalid credentials.",loginPage.errorText());
    }
    @Test
    public void testSuccessfulAddNewStudentsInGroup(){
        String groupName = "Group" + System.currentTimeMillis();
        MainPage mainPage = addNewGroup(groupName);

        int countOfStudents = 3;
        mainPage.successAddNStudentsInGroup(groupName, countOfStudents);

        mainPage.showStudentsInGroup(groupName);
        assertEquals(countOfStudents, mainPage.sizeOfStudentList());

        String studentName = mainPage.getStudentNameByIndex(1);
        assertEquals("active", mainPage.getStudentStatus(studentName));

        mainPage.deleteStudentRow(studentName);
        assertEquals("block",mainPage.getStudentStatus(studentName));

        mainPage.restoreStudentRow(studentName);
        assertEquals("active", mainPage.getStudentStatus(studentName));
    }

    @AfterEach
    public void close() {
        driver.quit();
    }

    private void login() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.successfulLogin(USERNAME, PASSWORD);
        loginPage.checkButtonInvisible();

        MainPage mainPage = new MainPage(driver, wait);
        String greetings = mainPage.getGreeting();
        assertEquals("Hello, %s".formatted(USERNAME), greetings);
    }

    private MainPage addNewGroup(String groupName) {
        login();
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.successAddNewGroup(groupName);
        return mainPage;
    }

}
