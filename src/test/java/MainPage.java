
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPage {
    private WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css = "nav li.mdc-menu-surface--anchor a")
    WebElement helloUser;
    @FindBy(css ="button#create-btn")
    WebElement addGroupIcon;
    @FindBy(css = "form#update-item input[type='text']")
    WebElement addGroupField;
    @FindBy(css = "form#update-item button")
    WebElement addGroupButton;
    @FindBy(xpath = "//*[@id='app']/main/div/div/div[2]/div[2]/div/div[1]/button")
    WebElement closeButton;
    @FindBy(css = "div.mdc-data-table")
    WebElement groupTable;
    @FindBy(css = "table[aria-label='Tutors list'] tbody tr")
    List<WebElement> listTableRows;
    @FindBy(css = "form#generate-logins input[type='number']")
    WebElement addStudentField;
    @FindBy(css = "form#generate-logins button")
    WebElement addStudentButton;
    @FindBy(xpath = "//*[@id=\"app\"]/main/div/div/div[3]/div[2]/div/div[1]/button")
    WebElement closeNewStudentsFormButton;
    @FindBy(css = "table[aria-label='User list'] tbody tr")
    List<WebElement> studentList;


    public MainPage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver,this);
    }

    public String getGreeting(){
        return wait.until(ExpectedConditions.visibilityOf(helloUser)).getText();
    }
    private void addNewGroup(String groupName) {
        wait.until(ExpectedConditions.visibilityOf(addGroupIcon));
        addGroupIcon.click();

        wait.until(ExpectedConditions.visibilityOf(addGroupField));
        addGroupField.sendKeys(groupName);

        wait.until(ExpectedConditions.visibilityOf(addGroupButton));
        addGroupButton.click();

        wait.until(ExpectedConditions.visibilityOf(closeButton));
        closeButton.click();
    }

    public void successAddNewGroup(String groupName){
        addNewGroup(groupName);
        wait.until(ExpectedConditions.textToBePresentInElement(groupTable, groupName));
    }

    public TableRow getRow(String groupName){
        return listTableRows.stream()
                .map(x -> new TableRow(x))
                .filter(x -> x.getTitle().equals(groupName))
                .findFirst()
                .orElseThrow();
    }

    public String getStatusRow(String groupName){
        return getRow(groupName).getStatus();
    }

    public void deleteRow(String groupName) {
        getRow(groupName).clickDelete("delete");
        getRow(groupName).waitRestore("restore_from_trash");
    }

    public void restoreRow(String groupName) {
        getRow(groupName).clickDelete("restore_from_trash");
        getRow(groupName).waitRestore("delete");
    }

    private void addNStudentsInGroup(String groupName, int number){
        getRow(groupName).addStudents();
        wait.until(ExpectedConditions.visibilityOf(addStudentField));
        addStudentField.sendKeys(String.valueOf(number));

        wait.until(ExpectedConditions.visibilityOf(addStudentButton));
        addStudentButton.click();

        wait.until(ExpectedConditions.visibilityOf(closeNewStudentsFormButton));
        closeNewStudentsFormButton.click();
    }

    public void successAddNStudentsInGroup(String groupName, int number){
        addNStudentsInGroup(groupName, number);
        getRow(groupName).waitCount(number);
    }

    public void showStudentsInGroup(String groupName) {
        getRow(groupName).clickViewStudents();
    }

    private List<WebElement> findStudentList(){
        return wait.until(ExpectedConditions.visibilityOfAllElements(studentList));
    }

    public int sizeOfStudentList(){
        return findStudentList().size();
    }

    public StudentListRow getStudentRow(String name) {
        return studentList.stream()
                .map(x -> new StudentListRow(x))
                .filter(x -> x.getName().equals(name))
                .findFirst()
                .orElseThrow();
    }

    public String getStudentNameByIndex(int index) {
        return studentList.stream()
                .map(x -> new StudentListRow(x))
                .toList().get(index).getName();
    }

    public void deleteStudentRow(String name) {
        getStudentRow(name).clickDeleteStudent("delete");
        getStudentRow(name).waitStudentRestore("restore_from_trash");
    }

    public void restoreStudentRow(String name) {
        getStudentRow(name).clickDeleteStudent("restore_from_trash");
        getStudentRow(name).waitStudentRestore("delete");
    }

    public String getStudentStatus(String name){
        return getStudentRow(name).getStatus();
    }
}
