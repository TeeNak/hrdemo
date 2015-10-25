package com.github.teenak77.hrdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by teenak77 on 2015/06/14.
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    @RequestMapping(method = RequestMethod.GET)
    public String printLogin(ModelMap model) {
        return "login";
    }
}
