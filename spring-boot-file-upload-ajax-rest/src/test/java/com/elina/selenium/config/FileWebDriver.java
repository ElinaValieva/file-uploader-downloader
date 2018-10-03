package com.elina.selenium.config;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FileWebDriver extends ChromeDriver {

    private final static Integer SPEED = 1000;

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
