package com.ipiecoles.java.java320.controller;

import com.ipiecoles.java.java320.model.Employe;
import com.ipiecoles.java.java320.repository.EmployeRepository;
import com.ipiecoles.java.java320.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ModelAndView getAllEmployeSorted(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "Id") String sortProperty,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortDirection
    ){
        ModelAndView employeModelAndView = new ModelAndView("list");
        //sortProperty n'est pas un attribut d'Employé => 400 BAD REQUEST
        List<String> properties = Arrays.asList("id","matricule","nom","prenom","salaire","dateEmbauche");
        if(!properties.contains(sortProperty))
            throw new IllegalArgumentException("La propriété de tri " + sortProperty + "est incorrecte.");
        //Valeurs négatives pour page et size => 400 BAD REQUEST
        if(page < 0 || size <= 0)
            throw new IllegalArgumentException("Les arguments page et size doivent être positif.");
        //contraindre size <= 50 => 400 BAD REQUEST
        if(size > 50)
            throw new IllegalArgumentException("L'argument size doit être inférieur ou égale à 50.");
        //page et size cohérents par rapport au nombre de lignes de la table => 400 BAD REQUEST
        if(page*size > employeService.countAllEmploye())
            throw new IllegalArgumentException("Les arguments page et size doivent représenter des valeurs existantes.");
        employeModelAndView.addObject("items", employeService.findAllEmployes(page, size, sortDirection.toString(), sortProperty));
        return employeModelAndView;
    }

}
