package ru.javarush.hikarius.testproject.crud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/*")
public class IndexController {
    @RequestMapping(method = RequestMethod.GET)
    public String viewApplication() {
        return "index";
    }
}
