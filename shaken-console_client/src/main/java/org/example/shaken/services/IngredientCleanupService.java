package org.example.shaken.services;

import org.example.shaken.model.Ingredient;
import org.example.shaken.model.IngredientRecipeDto;
import org.example.shaken.model.Recipe;

import java.util.List;

//Don't even look at this, this is just in case I need to do more cleanup
public class IngredientCleanupService {
    RecipeService recipeService = new RecipeService();
    IngredientService ingredientService = new IngredientService();
//    public void disgustingMessToAddNonDuplicateIngredients() {
//        List<String> ingredientList = recipeService.getIngredientList();
//        List<Ingredient> ingredientDB = ingredientService.getIngredients();
//        List<Recipe> results = new ArrayList<>();
//        for (String ingredientString : ingredientList) {
//            System.out.println(ingredientString);
//            String[] ingredientArray = ingredientString.split(" \\+ ");
//            for (String ingredient : ingredientArray) {
//                System.out.println(ingredient);
//                int quantityIndex = 1;
//                String measurement = null;
//
//                //FINDING QUANTITY
//                for (int i = 1; i < ingredient.length(); i++) {
//
//                    if (!Character.isDigit(ingredient.charAt(i)) && !Character.isDigit(ingredient.charAt(i + 1)) && !Character.isDigit(ingredient.charAt(i + 2))) {
//                        quantityIndex = i;
//                        break;
//                    }
//                }
//
//                //FINDING MEASUREMENT
//                if (ingredient.contains("ounce")) {
//                    measurement = "ounce";
//                } else if (ingredient.contains("dash")) {
//                    measurement = "dash";
//                } else if (ingredient.contains("teaspoon")) {
//                    measurement = "teaspoon";
//                } else if (ingredient.contains("tablespoon")) {
//                    measurement = "tablespoon";
//                } else if (ingredient.contains("cup")) {
//                    measurement = "cup";
//                } else if (ingredient.contains("drop")) {
//                    measurement = "drop";
//                } else if (ingredient.contains("pinch")) {
//                    measurement = "pinch";
//                } else if (ingredient.contains("scoop")) {
//                    measurement = "scoop";
//                }
//
//
//                String ingredientTitle = "";
//                int measurementIndex = 0;
//                if (measurement == null) {
//                    ingredientTitle = ingredient.substring(quantityIndex + 1);
//                } else {
//                    String[] array = ingredient.split(" ");
//                    boolean pastMeasurement = false;
//                    for (int i = 1; i < array.length; i++) {
//                        if (pastMeasurement) {
//                            ingredientTitle += array[i] + " ";
//                        }
//                        if (array[i].contains(measurement)) {
//                            pastMeasurement = true;
//                        }
//                    }
//                }
//
//                ingredientDB = ingredientService.getIngredients();
//
//                System.out.println(ingredient.substring(0, quantityIndex)); //QUANTITY
//                System.out.println(measurement); //MEASUREMENT
//                System.out.println(ingredientTitle.trim()); //TITLE
//
//                boolean duplicate = false;
//
//                if(ingredient.contains("Crushed Ice")) {
//                    Ingredient newIngredient = new Ingredient("1/2", "cup", "crushed ice");
//                    if (ingredientDB.size() == 0) {
//                        ingredientService.createIngredient(newIngredient);
//                    } else {
//                        for (Ingredient ing : ingredientDB) {
//                            if (ing.getMeasurement() != null && newIngredient.getMeasurement() != null) {
//                                if (ing.getMeasurement().equals(newIngredient.getMeasurement()) && ing.getQuantity().equals(newIngredient.getQuantity()) && ing.getName().equals(newIngredient.getName())) {
//                                    duplicate = true;
//                                }
//                            } else {
//                                if (ing.getQuantity().equals(newIngredient.getQuantity()) && ing.getName().equals(newIngredient.getName())) {
//                                    duplicate = true;
//                                }
//                            }
//                        }
//                        if (!duplicate) {
//                            ingredientService.createIngredient(newIngredient);
//                        }
//                    }
//                } else {
//                    Ingredient newIngredient = new Ingredient(ingredient.substring(0, quantityIndex), measurement, ingredientTitle.trim().toLowerCase());
//                    if(ingredientDB.size() == 0) {
//                        ingredientService.createIngredient(newIngredient);
//                    } else {
//                        for (Ingredient ing : ingredientDB) {
//                            if (ing.getMeasurement() != null && newIngredient.getMeasurement() != null) {
//                                if (ing.getMeasurement().equals(newIngredient.getMeasurement()) && ing.getQuantity().equals(newIngredient.getQuantity()) && ing.getName().equals(newIngredient.getName())) {
//                                    duplicate = true;
//                                    break;
//                                }
//                            } else {
//                                if (ing.getQuantity().equals(newIngredient.getQuantity()) && ing.getName().equals(newIngredient.getName())) {
//                                    duplicate = true;
//                                    break;
//                                }
//                            }
//                        }
//                        if (!duplicate) {
//                            ingredientService.createIngredient(newIngredient);
//                        }
//                    }
//                }
//
//            }
//        }
//    }

    public void addingIngredientsToRecipe() {
        List<Recipe> recipes = recipeService.getRecipes("");
        List<Ingredient> ingredients = ingredientService.getIngredients();

        for (Recipe recipe : recipes) {
            String recipeIngredientsString = recipe.getIngredients();
            String[] recipeIngredients = recipeIngredientsString.split(" \\+ ");
            for (Ingredient ingredient : ingredients) {
                //Checking for null getMeasurements()
                String ingString = "";
                String ingPluralString = "";
                String ingPluralString2 = "";
                if (ingredient.getMeasurement() != null) {
                    //Checking for instances of plural
                    ingString = ingredient.getQuantity() + " " + ingredient.getMeasurement() + " " + ingredient.getName();
                    ingPluralString = ingredient.getQuantity() + " " + ingredient.getMeasurement() + "s " + ingredient.getName();
                    ingPluralString2 = ingredient.getQuantity() + " " + ingredient.getMeasurement() + "es " + ingredient.getName();
                } else {
                    //Checking for instances of plural
                    ingString = ingredient.getQuantity() + " " + ingredient.getName();
                    ingPluralString = ingredient.getQuantity() + " " + ingredient.getName();
                    ingPluralString2 = ingredient.getQuantity() + " " + ingredient.getName();
                }
                for (String recipeIng : recipeIngredients) {
                    if (recipeIng.toLowerCase().equals(ingString.toLowerCase()) || recipeIng.toLowerCase().equals(ingPluralString.toLowerCase()) || recipeIng.toLowerCase().equals(ingPluralString2.toLowerCase())) {
                        IngredientRecipeDto ingredientRecipeDto = new IngredientRecipeDto(recipe.getRecipeId(), ingredient.getIngredientId());
                        ingredientService.mapIngredientToRecipe(ingredientRecipeDto);
                    }
                }
            }
        }
    }
}
