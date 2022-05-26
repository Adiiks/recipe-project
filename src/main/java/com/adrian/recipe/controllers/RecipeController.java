package com.adrian.recipe.controllers;

import com.adrian.recipe.commands.RecipeCommand;
import com.adrian.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/show/{recipeId}")
    public String showRecipeById(@PathVariable Long recipeId, Model model) {

        model.addAttribute("recipe", recipeService.findById(recipeId));

        return "recipe/show";
    }

    @RequestMapping("/new")
    public String newRecipe(Model model) {

        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeForm";
    }

    @PostMapping
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeToSave) {

        RecipeCommand savedRecipe = recipeService.saveRecipeCommand(recipeToSave);

        return "redirect:/recipe/show/" + savedRecipe.getId();
    }
}
