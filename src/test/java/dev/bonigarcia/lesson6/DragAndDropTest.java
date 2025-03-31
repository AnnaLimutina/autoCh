package dev.bonigarcia.lesson6;

import dev.bonigarcia.common.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class DragAndDropTest extends BaseTest {

    @Test
    public void rightClickDropDown() {
        getDriver().findElement(By.linkText("Drag and drop")).click();

        Actions actions = new Actions(getDriver());
        WebElement dragAndDropPanel = getDriver().findElement(By.id("draggable"));
        WebElement target = getDriver().findElement(By.id("target"));

        actions.clickAndHold(dragAndDropPanel).moveToElement(target).release(target).perform();

        Assert.assertEquals(dragAndDropPanel.getCssValue("left"), "660px");
        Assert.assertEquals(dragAndDropPanel.getCssValue("top"), "0px");
    }
}
