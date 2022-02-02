package com.ipiecoles.java.java320.controller;

import com.ipiecoles.java.java320.model.Employe;
import com.ipiecoles.java.java320.repository.EmployeRepository;
import com.ipiecoles.java.java320.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

@Controller
public class EmployeController {

    @Autowired
    private EmployeService employeService;

    @Autowired
    private EmployeRepository employeRepository;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/employes/{id}"
    )
    public ModelAndView detailEmploye(@PathVariable Long id){
        ModelAndView model = new ModelAndView("detail");
        model.addObject("employe", employeService.findById(id));
        return model;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/employes",
            produces = MediaType.APPLICATION_JSON_VALUE,
            params = "matricule"
    )
    public ModelAndView searchByMatricule(
            @RequestParam String matricule
    ){
        ModelAndView model = new ModelAndView("detail");
        model.addObject("employe", employeService.findMyMatricule(matricule));
        return model;
    }

    /*RequestMapping(
            method = RequestMethod.GET,
            value = "/employes"
    )
    public ModelAndView getAllEmployeSorted(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "Id") String sortProperty,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortDirection
    ){
        ModelAndView employeModelAndView = new ModelAndView("list");
        //sortProperty n'est pas un attribut d'Employé => 400 BAD REQUEST
        List<String> properties = Arrays.asList("id","matricule","nom","prenom","salaire","dateEmbauche");
        employeModelAndView.addObject("employes", employeService.findAllEmployes(page, size, sortProperty, sortDirection.toString()));
        /*employeModelAndView.addObject("size", size);
        employeModelAndView.addObject("page", page);
        return employeModelAndView;
    }*/
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/employes"
    )
    public ModelAndView listEmployes(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam String sortProperty,
            @RequestParam String sortDirection
    ){
        Page<Employe> employes = employeService.findAllEmployes(page, size, sortProperty, sortDirection);
        ModelAndView model = new ModelAndView("list");
        model.addObject("start", page * size + 1);
        model.addObject("end", page * size + employes.getNumberOfElements());
        model.addObject("employes", employes); // derniere page ara pas forcement 10 elements
        return model;
    }

}
