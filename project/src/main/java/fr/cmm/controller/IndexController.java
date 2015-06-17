package fr.cmm.controller;

import javax.inject.Inject;

import fr.cmm.controller.form.SearchForm;
import fr.cmm.helper.PageQuery;
import fr.cmm.helper.Pagination;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.cmm.helper.Columns;
import fr.cmm.service.RecipeService;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
        PageQuery pageQuery = new PageQuery();
        pageQuery.setIndex(searchForm.getPageIndex() - 1);
        pageQuery.setTag(searchForm.getTag());

        Pagination pagination = new Pagination();
        pagination.setPageIndex(searchForm.getPageIndex());
        pagination.setPageSize(pageQuery.getSize());
        pagination.setCount(recipeService.countByQuery(pageQuery));

        model.put("recipes", recipeService.findByQuery(pageQuery));
        model.put("pagination", pagination);

        return "recettes";
    }

    @RequestMapping("/recette-du-moment")
    public String recettesDuMoment(ModelMap model) {
        // BUG pick a recipe depending on season
        model.put("recipe", recipeService.findRandom(1).next());

        return "recette-du-moment";
    }

    private Columns randomColumns() {
        // BUG-12 : column height are too random ?
        Columns columns = new Columns();

        columns.add(recipeService.findRandom(10));
        columns.add(recipeService.findRandom(10));
        columns.add(recipeService.findRandom(10));

        return columns;
    }

    // BUG-10 : slug seo
    @RequestMapping("/recette/{id}")
    public String recette(@PathVariable("id") String id, ModelMap model) {
        // BUG-11 : page 404
        model.put("recipe", recipeService.findById(id));

        return "recette";
    }
}

