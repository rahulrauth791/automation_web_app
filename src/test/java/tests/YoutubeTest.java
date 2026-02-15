package tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import utils.BaseClass;

public class YoutubeTest extends BaseClass {

    @BeforeMethod
    public void setup() {
        initializeDriver();
    }

    @Test
    public void openYouTubeAndVerifyTitle() {
        getDriver().get("https://www.youtube.com/");
        String title = getDriver().getTitle();
        Assert.assertTrue(title.toLowerCase().contains("youtube"),
            "Expected page title to contain 'YouTube' but was: " + title);
    }

    @AfterMethod
    public void tearDown() {
        quitDriver();
    }
}