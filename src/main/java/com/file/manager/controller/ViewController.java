package com.file.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.file.manager.config.URLs.VIEW;
import static com.file.manager.config.URLs.VIEW_URL;

@Controller
public class ViewController {

    @GetMapping(VIEW_URL)
    public String index() {
        return VIEW;
    }

}