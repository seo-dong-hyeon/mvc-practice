package org.example.reflection.controller;

import org.example.reflection.annotaion.Controller;
import org.example.reflection.annotaion.RequestMapping;
import org.example.reflection.annotaion.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(HttpServletRequest request, HttpServletResponse response){
        return "home";
    }
}
