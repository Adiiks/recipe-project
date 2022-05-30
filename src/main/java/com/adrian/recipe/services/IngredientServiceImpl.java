package com.adrian.recipe.services;

import com.adrian.recipe.commands.IngredientCommand;
import com.adrian.recipe.converters.IngredientCommandToIngredient;
import com.adrian.recipe.converters.IngredientToIngredientCommand;
import com.adrian.recipe.domain.Ingredient;
import com.adrian.recipe.domain.Recipe;
import com.adrian.recipe.repositories.RecipeRepository;
import com.adrian.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            //todo
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert)
                .findFirst();

        if (!ingredientOptional.isPresent()) {
            //todo
        }

        return ingredientOptional.get();
    }

    @Transactional
    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientToSave) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientToSave.getRecipeId());

        if(!recipeOptional.isPresent()){

            //todo toss error if not found!
            return new IngredientCommand();
        } else {

            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientToSave.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){

                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientToSave.getDescription());
                ingredientFound.setAmount(ingredientToSave.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(ingredientToSave.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
            } else {

                //add new Ingredient
                recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientToSave));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            //to do check for fail
            return ingredientToIngredientCommand.convert(savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientToSave.getId()))
                    .findFirst()
                    .get());
        }

    }
}
