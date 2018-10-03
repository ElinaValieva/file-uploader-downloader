package com.elina.selenium.config;

public class SeleniumConfig {

    private  String URL = "http://localhost:8083/";

    private  String DRIVER = "webdriver.chrome.driver";

    private String LOCATION = "./chromedriver/chromedriver.exe";

    private Integer TIMEOUT = 20;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getDRIVER() {
        return DRIVER;
    }

    public void setDRIVER(String DRIVER) {
        this.DRIVER = DRIVER;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public Integer getTIMEOUT() {
        return TIMEOUT;
    }

    public void setTIMEOUT(Integer TIMEOUT) {
        this.TIMEOUT = TIMEOUT;
    }
}
