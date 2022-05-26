package com.adrian.recipe.services;

import com.adrian.recipe.commands.RecipeCommand;
import com.adrian.recipe.converters.RecipeCommandToRecipe;
import com.adrian.recipe.converters.RecipeToRecipeCommand;
import com.adrian.recipe.domain.Recipe;
import com.adrian.recipe.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    final Long ID = 1L;

    Recipe recipe;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);

        recipe = new Recipe();
        recipe.setId(ID);
    }

    @Test
    public void getRecipes() {

        Recipe recipe = new Recipe();
        HashSet<Recipe> recipesToReturn = new HashSet<>();
        recipesToReturn.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipesToReturn);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipesToReturn.size(), recipes.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void findById() {

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        Recipe returnedRecipe = recipeService.findById(ID);

        assertEquals(ID, returnedRecipe.getId());
    }

    @Test
    public void getRecipeCommandByIdTest() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        RecipeCommand commandById = recipeService.findCommandById(1L);

        assertNotNull("Null recipe returned", commandById);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }
}