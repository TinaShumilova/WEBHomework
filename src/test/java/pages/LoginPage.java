package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private SelenideElement buttonSubmit = $("form#login button");
    private SelenideElement userName = $("form#login input[type='text']");
    private SelenideElement userPassword = $("form#login input[type='password']");
    private SelenideElement invalidCredentialsErrorCode = $(".svelte-uwkxn9 h2");
    private SelenideElement invalidCredentialsErrorText = $(".svelte-uwkxn9 p");

    public void successfulLogin(String login, String password) {
        userName.should(Condition.visible).setValue(login);
        userPassword.should(Condition.visible).setValue(password);
        buttonSubmit.should(Condition.visible).click();
    }

    public void checkButtonInvisible() {
        buttonSubmit.should(Condition.disabled);
    }

    public void errorLogin() {
        buttonSubmit.should(Condition.visible).click();
    }

    public String errorCode() {
        return invalidCredentialsErrorCode.should(Condition.visible).getText();
    }

    public String errorText() {
        return invalidCredentialsErrorText.should(Condition.visible).getText();
    }
}
