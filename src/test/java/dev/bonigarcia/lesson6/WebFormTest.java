package dev.bonigarcia.lesson6;

import dev.bonigarcia.common.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;

public class WebFormTest extends BaseTest {

    @DataProvider
    public static Object[][] dropdownOptions() {
        return new Object[][]{
                {"1", "One"},
                {"2", "Two"},
                {"3", "Thre"}
        };
    }

    @DataProvider
    public static Object[][] inputData() {
        return new Object[][]{
                {"a"},
                {" "},
                {"!@#%^&*"}
        };
    }

    @Test(dataProvider = "inputData")
    public void testSuccessInput(String inputData) {
        getDriver().findElement(By.linkText("Web form")).click();
        WebElement textInput = getDriver().findElement(By.name("my-text"));
        textInput.sendKeys(inputData);

        String realValue = (String) getJs().executeScript("return arguments[0].value;", textInput);

        Assert.assertEquals(realValue, inputData, "the data is not displayed");
    }

    @Test
    public void testCheckPasswordIsHidden() {
        getDriver().findElement(By.linkText("Web form")).click();
        WebElement passwordInput = getDriver().findElement(By.name("my-password"));
        passwordInput.sendKeys("12345");

        String realValue = (String) getJs().executeScript("return arguments[0].value;", passwordInput);
        String inputType = passwordInput.getDomAttribute("type");

        Assert.assertEquals(realValue, "12345", "The password is not displayed");
        Assert.assertEquals(inputType, "password", "Input type is wrong");
    }

    @Test
    public void testDisabledInput() {
        getDriver().findElement(By.linkText("Web form")).click();
        WebElement disabledInput = getDriver().findElement(By.name("my-disabled"));

        Assert.assertFalse(disabledInput.isEnabled(), "The input is not disabled");
    }

    @Test
    public void testDropDown() {
        getDriver().findElement(By.linkText("Web form")).click();
        WebElement dropdown = getDriver().findElement(By.name("my-select"));
        Select select = new Select(dropdown);

        Assert.assertEquals(select.getFirstSelectedOption().getText(), "Open this select menu");
        Assert.assertEquals(select.getOptions().size(), 4);
    }

    @Test(dataProvider = "dropdownOptions")
    public void testSelectFromDropdown(String item, String itemValue) {
        getDriver().findElement(By.linkText("Web form")).click();
        WebElement dropdown = getDriver().findElement(By.name("my-select"));
        Select select = new Select(dropdown);
        select.selectByValue(item);

        Assert.assertEquals(select.getFirstSelectedOption().getText(), itemValue, item + " wasn't selected");
    }

    @Test
    public void testDataList() {
        getDriver().findElement(By.linkText("Web form")).click();
        String placeholder = getDriver().findElement(By.name("my-datalist")).getDomAttribute("placeholder");
        WebElement dataList = getDriver().findElement(By.name("my-options"));
        Select dl = new Select(dataList);

        Assert.assertEquals(placeholder, "Type to search...");
        Assert.assertEquals(dl.getOptions().size(), 5);
    }

    @Test
    public void testFileInput() {
        getDriver().findElement(By.linkText("Web form")).click();
        File uploadFile = new File("src/test/resources/uploadFiles/java.png");

        WebElement fileInput = getDriver().findElement(By.name("my-file"));
        fileInput.sendKeys(uploadFile.getAbsolutePath());

        String currentName = (String) getJs().executeScript("return arguments[0].value", fileInput);

        Assert.assertNotNull(currentName, "File wasn't uploaded");
        Assert.assertTrue(currentName.contains("java.png"), "Input doesn't contain name");
    }

    @Test
    public void testCheckboxes() {
        getDriver().findElement(By.linkText("Web form")).click();

        boolean isCheckedCheckboxSelected = getDriver().findElement(By.id("my-check-1")).isSelected();
        boolean isDefaultCheckboxSelected = getDriver().findElement(By.id("my-check-2")).isSelected();

        Assert.assertTrue(isCheckedCheckboxSelected, "Checked Checkbox is not selected");
        Assert.assertFalse(isDefaultCheckboxSelected, "Default Checkbox is selected");
    }

    @Test
    public void testUnselectCheckedCheckbox() {
        getDriver().findElement(By.linkText("Web form")).click();

        WebElement checkedCheckbox = getDriver().findElement(By.id("my-check-1"));
        checkedCheckbox.click();

        Assert.assertFalse(checkedCheckbox.isSelected(), "Checked Checkbox wasn't unselected");
    }

    @Test
    public void testSelectDefaultCheckbox() {
        getDriver().findElement(By.linkText("Web form")).click();

        WebElement defaultCheckbox = getDriver().findElement(By.id("my-check-2"));
        defaultCheckbox.click();

        Assert.assertTrue(defaultCheckbox.isSelected(), "Default Checkbox was not selected");
    }

    @Test
    public void testDataPicker() {
        getDriver().findElement(By.linkText("Web form")).click();
        WebElement dataPicker = getDriver().findElement(By.name("my-date"));
        dataPicker.click();

        getDriver().findElement(By.xpath("//td[@data-date='1741910400000']")).click();
        String DatePickerValue = dataPicker.getDomProperty("value");

        Assert.assertEquals(DatePickerValue, "03/14/2025", "Date is not displayed");
    }

    @Test
    public void testExampleRangeMoveForward() {
        getDriver().findElement(By.linkText("Web form")).click();

        WebElement exampleRange = getDriver().findElement(By.name("my-range"));
        String value = exampleRange.getDomAttribute("value");

        Actions actions = new Actions(getDriver());
        actions.clickAndHold(exampleRange)
                .moveByOffset(50, 0)
                .release()
                .perform();

        String newValue = (String) getJs().executeScript("return arguments[0].value", exampleRange);

        Assert.assertNotNull(newValue, newValue + " is null");
        Assert.assertNotNull(value, value + " is null");
        Assert.assertNotEquals(value, newValue, "Slider didn't move");
        Assert.assertTrue(Integer.parseInt(value) < Integer.parseInt(newValue));
    }

    @Test
    public void testExampleRangeMoveBack() {
        getDriver().findElement(By.linkText("Web form")).click();

        WebElement exampleRange = getDriver().findElement(By.name("my-range"));
        String value = exampleRange.getDomAttribute("value");

        getJs().executeScript("arguments[0].value= 1; arguments[0].dispatchEvent(new Event('input'))", exampleRange);

        String newValue = (String) getJs().executeScript("return arguments[0].value", exampleRange);

        Assert.assertNotNull(newValue, newValue + " is null");
        Assert.assertNotNull(value, value + " is null");
        Assert.assertNotEquals(value, newValue, "Slider didn't move");
        Assert.assertTrue(Integer.parseInt(newValue) < Integer.parseInt(value));
    }
}
