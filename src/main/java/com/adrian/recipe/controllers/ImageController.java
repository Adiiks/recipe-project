package com.adrian.recipe.controllers;

import com.adrian.recipe.commands.RecipeCommand;
import com.adrian.recipe.services.ImageService;
import com.adrian.recipe.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

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

    @GetMapping("/recipe-image")
    public void renderImage(@PathVariable Long recipeId, HttpServletResponse response) throws IOException {

        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);

        if (recipeCommand.getImage() != null) {

            byte[] bytes = new byte[recipeCommand.getImage().length];

            int i = 0;
            for (Byte b : recipeCommand.getImage()) {
                bytes[i++] = b;
            }

            response.setContentType("image/jpeg");
            InputStream inputStream = new ByteArrayInputStream(bytes);
            IOUtils.copy(inputStream, response.getOutputStream());
        }
    }
}
