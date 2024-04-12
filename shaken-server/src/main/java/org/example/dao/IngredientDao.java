package org.example.dao;

import org.example.model.Ingredient;
import org.example.model.IngredientRecipeDto;

import java.util.List;

public interface IngredientDao {
    List<Ingredient> getIngredients();
    List<Ingredient> getIngredientsByRecipe(int recipeId);
    Ingredient getIngredientById(int ingredientId);
    Ingredient createIngredient(Ingredient newIngredient, int recipeId);
    Ingredient findIngredient(Ingredient ingredient);
    Ingredient updateIngredient(Ingredient updatedIngredient);

    boolean isIngredientMappedToRecipe(IngredientRecipeDto ingredientRecipeDto);
    int removeIngredientFromRecipe(int ingredientId, int recipeId);
    void mapIngredientToRecipe(IngredientRecipeDto ingredientRecipeDto);
}
