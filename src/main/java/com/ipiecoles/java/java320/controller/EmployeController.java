package com.ipiecoles.java.java320.controller;

import com.ipiecoles.java.java320.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmployeController {

    @Autowired
    private EmployeService employeService;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/employes/{id}"
    )
    public ModelAndView detailEmploye(@PathVariable Long id){
        ModelAndView model = new ModelAndView("detail");
        model.addObject("employe", employeService.findById(id));
        return model;
    }

}
