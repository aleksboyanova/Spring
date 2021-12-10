package com.uni.project.controllers;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
    public ModelAndView makeModelAndViewFromViewName(String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(name);
        return modelAndView;
    }
}
