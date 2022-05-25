package com.adrian.recipe.controllers;

import com.adrian.recipe.domain.Recipe;
import com.adrian.recipe.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    RecipeController recipeController;

    MockMvc mockMvc;

    Recipe recipe;
    final Long ID = 1L;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        recipeController = new RecipeController(recipeService);

        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        recipe = new Recipe();
        recipe.setId(ID);
    }

    @Test
    public void getRecipe() throws Exception {

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/show/" + ID))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }
}
