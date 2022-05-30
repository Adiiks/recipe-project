package com.adrian.recipe.services;

import com.adrian.recipe.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientToSave);

    void deleteById(Long recipeId, Long ingredientId);
}
