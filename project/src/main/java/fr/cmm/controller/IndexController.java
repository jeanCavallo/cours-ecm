package fr.cmm.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.cmm.helper.Columns;
import fr.cmm.service.ReceipeService;

@Controller
public class IndexController {
    @Inject
    private ReceipeService receipeService;

    @RequestMapping({"/index", "/"})
    public String index(ModelMap model) {
        model.put("columns", randomColumns());

        return "index";
    }

    @RequestMapping("/recettes")
    public String recettes() {
        return "recettes";
    }

    @RequestMapping("/recettes-du-moment")
    public String recettesDuMoment() {
        return "recettes-du-moment";
    }

    private Columns randomColumns() {
        // BUG-12 : column height are too random ?
        Columns columns = new Columns();

        columns.add(receipeService.findRandom(10));
        columns.add(receipeService.findRandom(10));
        columns.add(receipeService.findRandom(10));

        return columns;
    }

    // BUG-10 : slug seo
    @RequestMapping("/recette/{id}")
    public String recette(@PathVariable("id") String id, ModelMap model) {
        // BUG-11 : page 404
        model.put("receipe", receipeService.findById(id));

        return "recette";
    }
}

