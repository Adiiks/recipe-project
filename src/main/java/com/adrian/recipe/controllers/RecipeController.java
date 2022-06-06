package com.adrian.recipe.controllers;

import com.adrian.recipe.commands.RecipeCommand;
import com.adrian.recipe.exceptions.NotFoundException;
import com.adrian.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private static final String RECIPE_FORM_URL = "recipe/recipeForm";

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{recipeId}/show")
    public String showRecipeById(@PathVariable Long recipeId, Model model) {

        model.addAttribute("recipe", recipeService.findById(recipeId));

        return "recipe/show";
    }

    @GetMapping("/new")
    public String newRecipe(Model model) {

        model.addAttribute("recipe", new RecipeCommand());

        return RECIPE_FORM_URL;
    }

    @PostMapping
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand recipeToSave, BindingResult result) {

        if (result.hasErrors()) {

            result.getAllErrors().forEach(objectError ->
                    log.debug(objectError.toString()));

            return RECIPE_FORM_URL;
        }

        RecipeCommand savedRecipe = recipeService.saveRecipeCommand(recipeToSave);

        return "redirect:/recipe/" + savedRecipe.getId() + "/show";
    }

    @GetMapping("/{recipeId}/update")
    public String updateRecipe(@PathVariable Long recipeId, Model model) {

        model.addAttribute("recipe", recipeService.findCommandById(recipeId));

        return RECIPE_FORM_URL;
    }

    @GetMapping("/{recipeId}/delete")
    public String deleteRecipe(@PathVariable Long recipeId) {

        recipeService.deleteById(recipeId);

        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {

        log.error("Handling not found exception");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }
}
