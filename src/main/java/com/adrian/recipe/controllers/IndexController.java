package com.adrian.recipe.controllers;

import com.adrian.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping({"", "/", "/index"})
    public String showIndexPage(Model model) {

        log.debug("Inside of the index controller");

        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }
}
