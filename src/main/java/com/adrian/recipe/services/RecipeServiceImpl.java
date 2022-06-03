package com.adrian.recipe.services;

import com.adrian.recipe.commands.RecipeCommand;
import com.adrian.recipe.converters.RecipeCommandToRecipe;
import com.adrian.recipe.converters.RecipeToRecipeCommand;
import com.adrian.recipe.domain.Recipe;
import com.adrian.recipe.exceptions.NotFoundException;
import com.adrian.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
                             RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {

        log.debug("In the service");

        Set<Recipe> recipes = new HashSet<>();

        recipeRepository.findAll().forEach(recipes::add);

        return recipes;
    }

    @Override
    public Recipe findById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe not found. ID: " + id));
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {

        Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);

        recipe = recipeRepository.save(recipe);

        return recipeToRecipeCommand.convert(recipe);
    }

    @Transactional
    @Override
    public RecipeCommand findCommandById(Long recipeId) {

        Recipe recipe = findById(recipeId);

        return recipeToRecipeCommand.convert(recipe);
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
