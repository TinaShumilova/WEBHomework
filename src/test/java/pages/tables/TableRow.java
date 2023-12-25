package pages.tables;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

public class TableRow {
    private final SelenideElement root;

    public TableRow(SelenideElement webElement) {
        this.root = webElement;
    }

    public String getTitle() {
        return root.$x("./td[2]").should(Condition.visible).getText();
    }

    public String getStatus() {
        return root.$x("./td[3]").should(Condition.visible).getText();
    }

    public void clickDelete(String text) {
        String buttonText = String.format("./td/button[text()='%s']", text);
        root.$x(buttonText).should(Condition.visible).click();
    }

    public void waitRestore(String text) {
        String buttonText = String.format("./td/button[text()='%s']", text);
        root.$x(buttonText).should(Condition.visible);
    }

    public void addStudents() {
        root.$x("./td[4]/button").should(Condition.visible).click();
    }

    public void waitCount(int number) {
        root.$x("./td[4]//span[text()='%s']".formatted(number)).should(Condition.visible);
    }

    public void clickViewStudents() {
        String buttonText = String.format("./td/button[text()='%s']", "zoom_in");
        root.$x(buttonText).should(Condition.visible).click();
    }

}
