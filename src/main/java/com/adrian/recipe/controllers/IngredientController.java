package com.adrian.recipe.controllers;

import com.adrian.recipe.commands.IngredientCommand;
import com.adrian.recipe.commands.RecipeCommand;
import com.adrian.recipe.commands.UnitOfMeasureCommand;
import com.adrian.recipe.services.IngredientService;
import com.adrian.recipe.services.RecipeService;
import com.adrian.recipe.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/{recipeId}/ingredients")
    public String listIngredients(@PathVariable Long recipeId, Model model) {

        model.addAttribute("recipe", recipeService.findCommandById(recipeId));

        return "recipe/ingredient/list";
    }

    @GetMapping("/{recipeId}/ingredient/{ingredientId}/show")
    public String showIngredient(@PathVariable Long recipeId, @PathVariable Long ingredientId, Model model) {

        model.addAttribute("ingredient",
                ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));

        return "recipe/ingredient/show";
    }

    @GetMapping
    @RequestMapping("/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(@PathVariable Long recipeId,
                                         @PathVariable Long ingredientId, Model model){
        model.addAttribute("ingredient",
                ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientForm";
    }

    @PostMapping("/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientToSave){

        IngredientCommand savedIngredient = ingredientService.saveIngredientCommand(ingredientToSave);

        log.debug("saved receipe id:" + savedIngredient.getRecipeId());
        log.debug("saved ingredient id:" + savedIngredient.getId());

        return "redirect:/recipe/" + savedIngredient.getRecipeId() + "/ingredient/"
                + savedIngredient.getId() + "/show";
    }

    @GetMapping("/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable Long recipeId, Model model){

        //make sure we have a good id value
        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);
        //todo raise exception if null

        //need to return back parent id for hidden form property
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeId);
        model.addAttribute("ingredient", ingredientCommand);

        //init uom
        ingredientCommand.setUom(new UnitOfMeasureCommand());

        model.addAttribute("uomList",  unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientForm";
    }

    @GetMapping("/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable Long recipeId, @PathVariable Long ingredientId) {

        ingredientService.deleteById(recipeId, ingredientId);

        return "redirect:/recipe/1/ingredients";
    }
}
