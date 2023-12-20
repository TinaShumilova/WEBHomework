import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.function.Function;

public class StudentListRow {
    private final WebElement root;

    public StudentListRow(WebElement root) {
        this.root = root;
    }

    public String getName(){
        return root.findElement(By.xpath("./td[2]")).getText();
    }

    public void clickDeleteStudent(String text) {
        String buttonText = String.format("./td/button[text()='%s']",text);
        root.findElement(By.xpath(buttonText)).click();
    }

    public void waitStudentRestore(String text){
        String buttonText = String.format("./td/button[text()='%s']",text);
        waitUntil(root -> root.findElement(By.xpath(buttonText)));
    }

    public String getStatus() {
        return root.findElement(By.xpath("./td[4]")).getText();
    }

    private void waitUntil(Function<WebElement, WebElement> until){
        new FluentWait<>(root)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class)
                .until(until);
    }
}
