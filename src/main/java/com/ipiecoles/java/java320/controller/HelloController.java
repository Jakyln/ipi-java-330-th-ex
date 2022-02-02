package com.ipiecoles.java.java320.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/hi"
    )
    public String sayHello(final ModelMap model){ // clé et map, automatiquement fourni par Spring
        model.put("nom", "IPI"); // nom est la variable qu'on envoie, IPI est son contenu
        return "hello"; // nom du template : hello.html
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/hi2"
    )
    public ModelAndView sayHello2(){
        ModelAndView modelAndView = new ModelAndView("hello");
        modelAndView.addObject("nom", "IPI");
        return modelAndView;
    }



}
