package com.adrian.recipe.services;

import com.adrian.recipe.commands.RecipeCommand;
import com.adrian.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    RecipeCommand findCommandById(Long recipeId);

    void deleteById(Long id);
}
