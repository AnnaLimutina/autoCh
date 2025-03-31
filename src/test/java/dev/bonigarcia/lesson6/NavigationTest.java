package dev.bonigarcia.lesson6;

import dev.bonigarcia.common.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NavigationTest extends BaseTest {
    @Test
    public void testOpenNavigationPage() {
        getDriver().findElement(By.linkText("Navigation")).click();

        String pageNumber = getDriver().findElement(By.cssSelector("li.page-item.active")).getText();
        boolean isPreviousButtonDisabled = Objects.requireNonNull(getDriver().findElement(By.xpath("//a[contains(text(), 'Previous')]/..")).getDomAttribute("class")).contains("disabled");
        String buttonColor = getDriver().findElement(By.xpath("//a[contains(text(), '1')]")).getCssValue("background-color");

        Assert.assertEquals(pageNumber, "1", "Page is not the first");
        Assert.assertTrue(isPreviousButtonDisabled);
        Assert.assertEquals(buttonColor, "rgba(13, 110, 253, 1)", "Button must be blue");
    }

    @Test
    public void testNavigation() {
        getDriver().findElement(By.linkText("Navigation")).click();

        List<String> text = new ArrayList<>(List.of("Lorem ipsum dolor sit amet", "Ut enim ad minim veniam", "Excepteur sint occaecat cupidatat non proident"));

        for (int i = 1; i <= 3; i++) {
            getDriver().findElement(By.linkText(String.valueOf(i))).click();
            String pageText = getDriver().findElement(By.className("lead")).getText();
            boolean isPreviousButtonDisabled = Objects.requireNonNull(getDriver().findElement(By.
                            xpath("//a[contains(text(), 'Previous')]/..")).getDomAttribute("class"))
                    .contains("disabled");
            boolean isNextButtonDisabled = Objects.requireNonNull(getDriver().findElement(By.
                            xpath("//a[contains(text(), 'Next')]/..")).getDomAttribute("class"))
                    .contains("disabled");

            Assert.assertTrue(pageText.contains(text.get(i - 1)));
            if(i == 1) {
                Assert.assertTrue(isPreviousButtonDisabled);
            } else {
                Assert.assertFalse(isPreviousButtonDisabled);
            }
            if(i == 3) {
                Assert.assertTrue(isNextButtonDisabled);
            } else {
                Assert.assertFalse(isNextButtonDisabled);
            }
        }
    }
}
