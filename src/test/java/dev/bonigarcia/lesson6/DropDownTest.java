package dev.bonigarcia.lesson6;

import dev.bonigarcia.common.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class DropDownTest extends BaseTest {

    @Test
    public void leftClickDropDown() {
        getDriver().findElement(By.linkText("Dropdown menu")).click();

        Actions actions = new Actions(getDriver());
        WebElement leftClickDropDown = getDriver().findElement(By.id("my-dropdown-1"));

        actions.moveToElement(leftClickDropDown).click().perform();

        Assert.assertEquals(leftClickDropDown.getDomAttribute("aria-expanded"), "true");
            }

    @Test
    public void rightClickDropDown() {
        getDriver().findElement(By.linkText("Dropdown menu")).click();

        Actions actions = new Actions(getDriver());
        WebElement rightClickDropDown = getDriver().findElement(By.id("my-dropdown-2"));

        actions.moveToElement(rightClickDropDown).contextClick(rightClickDropDown).perform();

        Assert.assertTrue(Objects.requireNonNull(getDriver().findElement(By.id("context-menu-2")).getDomAttribute("style")).contains("block"));
    }

    @Test
    public void doubleClickDropDown() {
        getDriver().findElement(By.linkText("Dropdown menu")).click();

        Actions actions = new Actions(getDriver());
        WebElement doubleClickDropDown = getDriver().findElement(By.id("my-dropdown-3"));
        actions.moveToElement(doubleClickDropDown).doubleClick(doubleClickDropDown).perform();

        Assert.assertTrue(Objects.requireNonNull(getDriver().findElement(By.id("context-menu-3")).getDomAttribute("style")).contains("block"));
    }
}
