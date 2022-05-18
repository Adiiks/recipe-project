package com.adrian.recipe.controllers;

import com.adrian.recipe.domain.Category;
import com.adrian.recipe.domain.UnitOfMeasure;
import com.adrian.recipe.repositories.CategoryRepository;
import com.adrian.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository uomRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository uomRepository) {
        this.categoryRepository = categoryRepository;
        this.uomRepository = uomRepository;
    }

    @RequestMapping({"", "/", "/index"})
    public String showIndexPage() {

        Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> uomOptional = uomRepository.findByDescription("Teaspoon");

        System.out.println("Cat ID is: " + categoryOptional.get().getId());
        System.out.println("UOM ID is: " + uomOptional.get().getId());

        return "index";
    }
}
