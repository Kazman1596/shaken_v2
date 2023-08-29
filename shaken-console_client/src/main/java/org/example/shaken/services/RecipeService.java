package org.example.shaken.services;

import org.example.shaken.model.Ingredient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.example.shaken.model.Recipe;
import org.example.util.BasicLogger;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RecipeService {
    private static final String API_BASE_URL = "http://localhost:8080/recipes/";

    private final RestTemplate restTemplate = new RestTemplate();

    //For ingredientMap
    IngredientService ingredientService = new IngredientService();

    public List<Recipe> getRecipes(String title) {
        Recipe[] recipes = null;

        try{
            String url = API_BASE_URL + "?title=" + title;
            recipes = restTemplate.getForObject(url, Recipe[].class);
        } catch (RestClientException e) {
            BasicLogger.log(e.getMessage());
        }

        if (recipes != null) {
            return Arrays.asList(recipes);
        } else {
            return new ArrayList<>();
        }
    }

    public Recipe getRecipeById(int id) {
        Recipe recipe = null;
        try{
            recipe = restTemplate.getForObject(API_BASE_URL + id, Recipe.class);
        } catch (RestClientException e) {
            BasicLogger.log(e.getMessage());
        }

        return recipe;
    }

    //TODO: HashMap is not the move. Suggested -> TreeMap... look into that
    public HashMap<String, List<Recipe>> getIngredientMap() {
        List<Ingredient> ingredients = ingredientService.getIngredients();
        HashMap<String, List<Recipe>> ingredientMap = new HashMap<>();
        for (Ingredient ingredient : ingredients) {
            List<Recipe> recipes = getRecipesByIngredientName(ingredient.getName());
            ingredientMap.put(ingredient.getName(), recipes);
        }

        return ingredientMap;
    }

    public List<Recipe> getRecipesByAccountId(int id) {
        Recipe[] recipes = null;
        try{
            String url = API_BASE_URL + "user/" + id + "/collection";
            recipes = restTemplate.getForObject(url, Recipe[].class);
        } catch (RestClientException e) {
            BasicLogger.log(e.getMessage());
        }

        if (recipes != null) {
            return Arrays.asList(recipes);
        } else {
            return new ArrayList<>();
        }
    }

    public List<Recipe> getRecipesByIngredientName(String name) {
        Recipe[] recipes = null;
        try{
            String url = API_BASE_URL + "/ingredient/" + name;
            recipes = restTemplate.getForObject(url, Recipe[].class);
        } catch (RestClientException e) {
            BasicLogger.log(e.getMessage());
        }

        return (recipes != null) ? Arrays.asList(recipes) : new ArrayList<>();

    }

    //TODO: Fix createRecipe to route ingredients (perhaps recipe model is includes List<Ingredient>
    public Recipe createRecipe(Recipe newRecipe) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Recipe> entity = new HttpEntity<>(newRecipe, headers);
        Recipe result = null;

        try {
            result = restTemplate.postForObject(API_BASE_URL, entity, Recipe.class);
            getRecipes("");
            getIngredientMap();
        }
        catch(RestClientResponseException e) {
            BasicLogger.log(e.getStatusText());
        }
        catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return result;
    }

    public Recipe updateRecipe(Recipe updatedRecipe) {
        String url = API_BASE_URL + updatedRecipe.getRecipeId();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Recipe> entity = new HttpEntity<>(updatedRecipe, headers);
        boolean success = false;

        try{
            restTemplate.put(url, entity);
            //Updating our local data every time Recipes is updated
            getRecipes("");
            getIngredientMap();
        }
        catch(RestClientResponseException e) {
            BasicLogger.log(e.getStatusText());
        }
        catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return getRecipeById(updatedRecipe.getRecipeId());
    }

    public void deleteRecipe(Recipe recipe) {
        String title = recipe.getTitle();
        String url = API_BASE_URL + recipe.getRecipeId();

        try{
            restTemplate.delete(url);
            System.out.println("Successfully deleted " + title);
        }
        catch(RestClientResponseException e) {
            BasicLogger.log(e.getStatusText());
        }
        catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
    }
}
