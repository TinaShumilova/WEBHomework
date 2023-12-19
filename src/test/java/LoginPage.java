import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css = "form#login button")
    WebElement buttonSubmit;
    @FindBy(css = "form#login input[type='text']")
    WebElement userName;
    @FindBy(css = "form#login input[type='password']")
    WebElement userPassword;

    public LoginPage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver,this);
    }

    public void login(String login, String password) {
        wait.until(ExpectedConditions.visibilityOf(userName));
        wait.until(ExpectedConditions.visibilityOf(userPassword));
        wait.until(ExpectedConditions.visibilityOf(buttonSubmit));

        userName.sendKeys(login);
        userPassword.sendKeys(password);
        buttonSubmit.click();
    }

    public void checkButtonInvisible(){
        wait.until(ExpectedConditions.invisibilityOf(buttonSubmit));
    }
}
