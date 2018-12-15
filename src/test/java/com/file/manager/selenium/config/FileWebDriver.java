package com.file.manager.selenium.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FileWebDriver extends ChromeDriver {

    private final static Integer SPEED = 1000;
    private WebDriver webDriver;

    public FileWebDriver(){
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
    }
    /**
     * used to set delay between actions
     * @param by
     * @return
     */
    @Override
    public WebElement findElement(By by) {
        try {
            Thread.sleep(this.SPEED);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return by.findElement(this);
    }
}
