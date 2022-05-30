package com.adrian.recipe.controllers;

import com.adrian.recipe.services.ImageService;
import com.adrian.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/recipe/{recipeId}")
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/image")
    public String showImageForm(Model model, @PathVariable Long recipeId) {

        model.addAttribute("recipe", recipeService.findCommandById(recipeId));

        return "recipe/imageUploadForm";
    }

    @PostMapping("/image")
    public String handleImagePost(@PathVariable Long recipeId, @RequestParam("imageFile") MultipartFile file) {

        imageService.saveImageFile(recipeId, file);

        return "redirect:/recipe/" + recipeId + "/show";
    }
}
