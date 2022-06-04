package com.gusstrik.vkr.service.userservice.web.page;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

@Controller
public class UserPageController {

    @GetMapping(value = "/main", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView main(HttpServletRequest servletRequest) throws Exception {
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }



}
