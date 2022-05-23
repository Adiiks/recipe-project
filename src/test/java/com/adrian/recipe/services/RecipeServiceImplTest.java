package com.adrian.recipe.services;

import com.adrian.recipe.domain.Recipe;
import com.adrian.recipe.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository);
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
}