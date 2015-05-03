package fr.cmm.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.cmm.domain.Receipe;
import fr.cmm.helper.PageQuery;
import fr.cmm.helper.Pagination;
import fr.cmm.service.ReceipeService;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Inject
    private ReceipeService receipeService;

    @RequestMapping("/recettes")
    public String index() {
        return "redirect:/admin/recettes";
    }

    @RequestMapping("/recettes")
    public String recettes(@RequestParam(defaultValue = "1") int index, ModelMap model) {
        // BUG-2 : index <= 0
        PageQuery query = new PageQuery();
        query.setIndex(index - 1);

        model.put("items", receipeService.findByQuery(query));

        Pagination pagination = new Pagination();
        pagination.setPageIndex(query.getIndex() + 1);
        pagination.setPageSize(query.getSize());
        pagination.setCount(receipeService.countByQuery(query));

        model.put("pagination", pagination);

        return "admin/receipe/list";
    }

    @RequestMapping("/recettes/edit")
    public String edit(@RequestParam(required = false) String id, ModelMap model) {
        if (id == null) {
            model.put("command", new Receipe());
        } else {
            model.put("command", receipeService.findById(id));
        }

        return "admin/receipe/form";
    }

    @RequestMapping(value = "/recettes/edit", method = POST)
    public String post(@ModelAttribute("command") @Valid Receipe receipe, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/receipe/form";
        }

        receipeService.save(receipe);

        redirectAttributes.addFlashAttribute("flashMessage", "La recette a été sauvée");

        return "redirect:/admin/recettes/edit?id=" + receipe.getId();
    }

    @RequestMapping("/recettes/ingredientFormRow")
    public String post(@RequestParam int ingredientIndex, ModelMap model) {
        model.put("ingredientIndex", ingredientIndex);

        return "admin/receipe/ingredient-form-row";
    }
}

