import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class TableRow {
    WebElement webElement;
    public TableRow(WebElement webElement){
        this.webElement = webElement;
    }

    public String getTitle(){
        return webElement.findElement(By.xpath("./td[2]")).getText();
    }

    public String getStatus(){
        return webElement.findElement(By.xpath("./td[3]")).getText();
    }

    public void clickDelete(String text) {
        String buttonText = String.format("./td/button[text()='%s']",text);
        webElement.findElement(By.xpath(buttonText)).click();
    }

    public void waitRestore(String text){
        String buttonText = String.format("./td/button[text()='%s']",text);
        FluentWait<WebElement> fluentWait =new FluentWait<>(this.webElement);
        fluentWait
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class)
                .until(x -> x.findElement(By.xpath(buttonText)));
    }
}
