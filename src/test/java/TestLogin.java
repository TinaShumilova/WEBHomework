import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLogin {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String USERNAME = "Student-1";
    private static final String PASSWORD = "59c5266dc9";
    private static final String FULLNAME = "1 Student";
    MainPage mainPage = Selenide.page(MainPage.class);
    private static String baseUrl = "https://test-stand.gb.ru/login";

    @BeforeEach
    public void open() {
        Selenide.open(baseUrl);
        driver = WebDriverRunner.getWebDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testNameInProfile(){
        login();
        mainPage.clickProfileButton();
        ProfilePage profilePage = Selenide.page(ProfilePage.class);
        assertEquals(FULLNAME, profilePage.getAdditionalInfoName());
        assertEquals(FULLNAME, profilePage.getAvatarInfoName());
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

        String studentName = mainPage.getStudentNameByIndex(0);
        assertEquals("active", mainPage.getStudentStatus(studentName));

        mainPage.deleteStudentRow(studentName);
        assertEquals("block",mainPage.getStudentStatus(studentName));

        mainPage.restoreStudentRow(studentName);
        assertEquals("active", mainPage.getStudentStatus(studentName));
    }

    @AfterEach
    public void close() {
        WebDriverRunner.closeWebDriver();
    }

    private void login() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.successfulLogin(USERNAME, PASSWORD);
        loginPage.checkButtonInvisible();

        String greetings = mainPage.getGreeting();
        assertEquals("Hello, %s".formatted(USERNAME), greetings);
    }

    private MainPage addNewGroup(String groupName) {
        login();
        mainPage.successAddNewGroup(groupName);
        return mainPage;
    }

}
