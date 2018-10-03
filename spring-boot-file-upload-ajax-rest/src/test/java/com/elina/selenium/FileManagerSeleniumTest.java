package com.elina.selenium;


import com.elina.selenium.config.FileWebDriver;
import com.elina.selenium.config.SeleniumConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileManagerSeleniumTest {

    private WebDriver webDriver;

    private SeleniumConfig seleniumConfig = new SeleniumConfig();

    private final String DATE = "12/10/2018";
    private final String TIME = "10:00:00";
    private final String ZONE = "AM";
    private String PATH;
    private final String TOKEN = "dGVzdC0oMSkudHh0";

    @Before
    public void init() throws FileNotFoundException {
        System.setProperty(seleniumConfig.getDRIVER(), seleniumConfig.getLOCATION());
        webDriver = new FileWebDriver();
        webDriver.manage().window().maximize();
        webDriver.get(seleniumConfig.getURL());
        File file = ResourceUtils.getFile("classpath:test.txt");
        PATH = file.getAbsolutePath();
    }

    @Test
    public void autoTest() {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, seleniumConfig.getTIMEOUT());
        webDriverWait.until(ExpectedConditions
                .presenceOfElementLocated(By.name("fileName")))
                .sendKeys("test");

        webDriverWait.until(ExpectedConditions
                .presenceOfElementLocated(By.name("dateDurationDescription")))
                .sendKeys(DATE + Keys.TAB + TIME + Keys.TAB + ZONE);

        webDriverWait.until(ExpectedConditions
                .presenceOfElementLocated(By.name("multipartFile")))
                .sendKeys(PATH);

        webDriverWait.until(ExpectedConditions
                .presenceOfElementLocated(By.id("btnSubmit")))
                .click();

        webDriverWait.until(ExpectedConditions
                .presenceOfElementLocated(By.id("tokenKey")))
                .sendKeys(TOKEN);

        webDriverWait.until(ExpectedConditions
                .presenceOfElementLocated(By.id("downloadBtn")))
                .click();
    }
}
