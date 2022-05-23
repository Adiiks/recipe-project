package com.adrian.recipe.controllers;

import com.adrian.recipe.services.RecipeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class IndexControllerTest {

    IndexController indexController;

    @Mock
    RecipeServiceImpl recipeService;

    @Mock
    Model model;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        indexController = new IndexController(recipeService);
    }

    @Test
    public void showIndexPage() {

        String templateName = "index";

        assertEquals(templateName, indexController.showIndexPage(model));
        verify(model, times(1)).addAttribute(eq("recipes"), anySet());
    }
}