
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    private SelenideElement helloUser = $("nav li.mdc-menu-surface--anchor a");
    private SelenideElement addGroupIcon = $("button#create-btn");
    private SelenideElement addGroupField = $("form#update-item input[type='text']");
    private SelenideElement addGroupButton = $("form#update-item button");
    private SelenideElement closeButton = $x("//*[@id='app']/main/div/div/div[2]/div[2]/div/div[1]/button");
    private SelenideElement groupTable = $("div.mdc-data-table");
    private ElementsCollection listTableRows = $$("table[aria-label='Tutors list'] tbody tr");
    private SelenideElement addStudentField = $("form#generate-logins input[type='number']");
    private SelenideElement addStudentButton = $("form#generate-logins button");
    private SelenideElement closeNewStudentsFormButton = $x("//*[@id=\"app\"]/main/div/div/div[3]/div[2]/div/div[1]/button");
    private ElementsCollection studentList = $$("table[aria-label='User list'] tbody tr");
    private SelenideElement profileButton = $x("//nav//li[contains(@class,'mdc-menu-surface--anchor')]//span[text()='Profile']");


    public String getGreeting(){
        return helloUser.should(Condition.visible).getText();
    }

    public void successAddNewGroup(String groupName){
        addNewGroup(groupName);
        groupTable.should(Condition.visible).should(Condition.text(groupName));
    }

    public TableRow getRow(String groupName){
        return listTableRows
                .asDynamicIterable()
                .stream()
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

    public void successAddNStudentsInGroup(String groupName, int number){
        addNStudentsInGroup(groupName, number);
        getRow(groupName).waitCount(number);
    }

    public void showStudentsInGroup(String groupName) {
        getRow(groupName).clickViewStudents();
    }

    public int sizeOfStudentList(){
        return findStudentList().size();
    }

    public StudentListRow getStudentRow(String name) {
        return studentList
                .asDynamicIterable()
                .stream()
                .map(x -> new StudentListRow(x))
                .filter(x -> x.getName().equals(name))
                .findFirst()
                .orElseThrow();
    }

    public String getStudentNameByIndex(int index) {
        return studentList
                .asDynamicIterable()
                .stream()
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

    public void clickProfileButton(){
        helloUser.should(Condition.visible).click();
        profileButton.should(Condition.visible).click();

    }

    private void addNewGroup(String groupName) {
        addGroupIcon.should(Condition.visible).click();
        addGroupField.should(Condition.visible).setValue(groupName);
        addGroupButton.should(Condition.visible).click();
        closeButton.should(Condition.visible).click();

    }

    private void addNStudentsInGroup(String groupName, int number){
        getRow(groupName).addStudents();
        addStudentField.should(Condition.visible).setValue(String.valueOf(number));

        addStudentButton.should(Condition.visible).click();

        closeNewStudentsFormButton.should(Condition.visible).click();

    }
    private ElementsCollection findStudentList(){
        return studentList.should(CollectionCondition.sizeGreaterThan(0));
    }
}
