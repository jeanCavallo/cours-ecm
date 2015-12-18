package fr.cmm.controller;

import javax.inject.Inject;

import fr.cmm.controller.form.SearchForm;
import fr.cmm.domain.Recipe;
import fr.cmm.helper.PageIndex;
import fr.cmm.helper.PageQuery;
import fr.cmm.helper.Pagination;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.cmm.helper.Columns;
import fr.cmm.service.RecipeService;

import java.util.List;

import static fr.cmm.helper.PageIndex.fixPageIndex;

@Controller
public class IndexController {
    @Inject
    private RecipeService recipeService;

    @RequestMapping({"/index", "/"})
    public String index(ModelMap model) {
        model.put("columns", randomColumns());

        return "index";
    }

    @RequestMapping("/tags.json")
    @ResponseBody
    public List<String> tags() {
        return recipeService.findAllTags();
    }

    @RequestMapping("/recettes")
    public String recettes(SearchForm searchForm, ModelMap model) {
        int safePageIndex = fixPageIndex(searchForm.getPageIndex());

        PageQuery pageQuery = new PageQuery();
        pageQuery.setIndex(safePageIndex - 1);
        pageQuery.setTag(searchForm.getTag());

        Pagination pagination = new Pagination();
        pagination.setPageIndex(safePageIndex);
        pagination.setPageSize(pageQuery.getSize());
        pagination.setCount(recipeService.countByQuery(pageQuery));

        model.put("recipes", recipeService.findByQuery(pageQuery));
        model.put("pagination", pagination);
        model.put("tagName", searchForm.getTag());

        return "recettes";
    }

    @RequestMapping("/recette-du-moment")
    public String recettesDuMoment(ModelMap model) {
        model.put("recipe", recipeService.findRandom(1).next());

        return "recette-du-moment";
    }

    private Columns randomColumns() {
        Columns columns = new Columns();

        columns.add(recipeService.findRandom(10));
        columns.add(recipeService.findRandom(10));
        columns.add(recipeService.findRandom(10));

        return columns;
    }

    @RequestMapping("/recette/{id}")
    public String recette(@PathVariable("id") String id, ModelMap model) {
        Recipe recipe = recipeService.findById(id);

        if(recipe == null) {
            throw new ResourceNotFoundException();
        }

        model.put("recipe", recipe);

        return "recette";
    }

    @RequestMapping("/contact")
    public String contact() {
        return "contact";
    }

    @RequestMapping("/mentions-legales")
    public String mentionsLegales() {
        return "mentions-legales";
    }

}



