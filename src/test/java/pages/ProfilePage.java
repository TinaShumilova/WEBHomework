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

}
