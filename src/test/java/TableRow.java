import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.function.Function;

public class TableRow {
    private final WebElement root;

    public TableRow(WebElement webElement){
        this.root = webElement;
    }

    public String getTitle(){
        return root.findElement(By.xpath("./td[2]")).getText();
    }

    public String getStatus(){
        return root.findElement(By.xpath("./td[3]")).getText();
    }

    public void clickDelete(String text) {
        String buttonText = String.format("./td/button[text()='%s']",text);
        root.findElement(By.xpath(buttonText)).click();
    }

    public void waitRestore(String text){
        String buttonText = String.format("./td/button[text()='%s']",text);
        waitUntil(root -> root.findElement(By.xpath(buttonText)));
    }

    public void addStudents(){
        root.findElement(By.xpath("./td[4]/button")).click();
    }

    public void waitCount(int number){
        waitUntil(root ->
                root.findElement(By.xpath("./td[4]//span[text()='%s']".formatted(number))));
    }


    public void clickViewStudents() {
        String buttonText = String.format("./td/button[text()='%s']","zoom_in");
        root.findElement(By.xpath(buttonText)).click();
    }

    private void waitUntil(Function<WebElement, WebElement> until){
        new FluentWait<>(root)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class)
                .until(until);
    }
}
