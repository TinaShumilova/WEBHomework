package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import net.bytebuddy.asm.Advice;

import java.io.File;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ProfilePage {
    private SelenideElement additionalInfoFullName = $x("//h3/following-sibling::div//div[contains(text(), 'Full name')]/following-sibling::div");
    private SelenideElement avatarFullName = $("div.mdc-card h2");
    private SelenideElement editButton = $("button[title='More options']");
    private SelenideElement formEditAvatar = $("form#update-item");
    private SelenideElement inputAvatarFileField = formEditAvatar.$("input[type='file']");
    private SelenideElement inputBirthDateField = formEditAvatar.$("input[type='date']");
    private SelenideElement submitButton = formEditAvatar.$("button[type='submit']");
    private SelenideElement closeEditForm = $x("//*[@id=\"app\"]/main/div/div/div[2]/div[2]/div/div[1]/button");
    private SelenideElement additionalInfoBirthDate = $x("//h3/following-sibling::div//div[contains(text(), 'Date of birth')]/following-sibling::div");

    public String getAdditionalInfoName() {
        return additionalInfoFullName.should(Condition.visible).getText();
    }

    public String getAvatarInfoName() {
        return avatarFullName.should(Condition.visible).getText();
    }

    public void clickEditButton(){
        editButton.should(Condition.visible).click();
    }

    public void uploadNewAvatar(File file){
        inputAvatarFileField.shouldBe(Condition.visible).uploadFile(file);
    }

    public String getAvatarValue(){
        return inputAvatarFileField.should(Condition.visible).getValue();
    }

    public void changeBirthdate(String date){
        inputBirthDateField.should(Condition.visible).setValue(date);
        submitButton.should(Condition.visible).click();
        closeEditForm.should(Condition.visible).click();
    }

    public String checkBirthDate(){
        return additionalInfoBirthDate.should(Condition.visible).getText();
    }




}
