package pages.tables;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

public class StudentListRow {
    private final SelenideElement root;

    public StudentListRow(SelenideElement root) {
        this.root = root;
    }

    public String getName() {
        return root.$x("./td[2]").should(Condition.visible).getText();
    }

    public void clickDeleteStudent(String text) {
        String buttonText = String.format("./td/button[text()='%s']", text);
        root.$x(buttonText).should(Condition.visible).click();
    }

    public void waitStudentRestore(String text) {
        String buttonText = String.format("./td/button[text()='%s']", text);
        root.$x(buttonText).should(Condition.visible);
    }

    public String getStatus() {
        return root.$x("./td[4]").should(Condition.visible).getText();
    }

}
