package com.adrian.recipe.services;

import com.adrian.recipe.domain.Recipe;
import com.adrian.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> getRecipes() {

        log.debug("In the service");

        return (List<Recipe>) recipeRepository.findAll();
    }
}
