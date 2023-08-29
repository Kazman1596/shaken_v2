package org.example.dao;

import org.example.exception.DaoException;
import org.example.model.Recipe;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcRecipeDao implements RecipeDao{

    private static final String RECIPE_SELECT_STRING = "SELECT title, ingredients, instructions, glass, account_id, rating, post_date, post_time, recipe_id FROM recipe WHERE active = true ";
    private static final String RECIPE_JOIN_STRING = "SELECT r.title, r.ingredients, r.instructions, r.glass, r.account_id, r.rating, r.post_date, r.post_time, r.recipe_id FROM recipe r ";
    private final JdbcTemplate jdbcTemplate;

    public JdbcRecipeDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Recipe> getRecipes() {
        List<Recipe> returnedRecipes = new ArrayList<>();
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(RECIPE_SELECT_STRING);
            while (results.next()) {
                Recipe recipe = mapRowToRecipe(results);
                returnedRecipes.add(recipe);
            }
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        }

        return returnedRecipes;
    }
    @Override
    public List<Recipe> getRecipesByTitle(String userInput, boolean wild) {
        List<Recipe> returnedRecipes = new ArrayList<>();
        String sql = RECIPE_SELECT_STRING + "AND title ILIKE ?;";
        if (wild) {
            userInput = "%" + userInput + "%";
        }
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userInput);
            while (results.next()) {
                Recipe recipe = mapRowToRecipe(results);
                returnedRecipes.add(recipe);
            }
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        }

        return returnedRecipes;
    }
    @Override
    public Recipe getRecipeById(int id) {
        Recipe recipe = null;
        String sql = RECIPE_SELECT_STRING + "AND recipe_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                recipe = mapRowToRecipe(results);
            }
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new DaoException("Data Integrity Violation", ex);
        }
        return recipe;
    }
    @Override
    public List<Recipe> getRecipesByAccountId(int accountId) {
        List<Recipe> returnedRecipes = new ArrayList<>();

        String sql = RECIPE_SELECT_STRING + "AND account_id = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
            while (results.next()) {
                Recipe recipe;
                recipe = mapRowToRecipe(results);
                returnedRecipes.add(recipe);
            }
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Cannot connect to server or database", ex);
        }

        return returnedRecipes;
    }

    @Override
    public Recipe createRecipe(Recipe newRecipe) {
        Recipe recipe = null;
        String sql = "INSERT INTO recipe(title, ingredients, instructions, glass, account_id, rating, post_date, post_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING recipe_id;";
        try {
            int newId = jdbcTemplate.queryForObject(sql, int.class, newRecipe.getTitle(), newRecipe.getIngredients(),
                    newRecipe.getInstructions(), newRecipe.getGlass(), newRecipe.getAccountId(), newRecipe.getRating(),
                    newRecipe.getPostDate(), newRecipe.getPostTime());
            recipe = getRecipeById(newId);
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to the server or database", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new DaoException("Data Integrity Violation", ex);
        }
        return recipe;
    }
    @Override
    public Recipe updateRecipe(Recipe recipe) {
        Recipe updatedRecipe = null;
        String sql = "UPDATE recipe SET title=?, ingredients=?, instructions=?, glass=?, account_id=?, rating=? WHERE recipe_id = ?;";
        try {
            int rowCount = jdbcTemplate.update(sql, recipe.getTitle(), recipe.getIngredients(), recipe.getInstructions(), recipe.getGlass(), recipe.getAccountId(), recipe.getRating(), recipe.getRecipeId());
            if (rowCount == 0) {
                throw new DaoException("Expected 1 updated recipe, but found 0");
            } else {
                updatedRecipe = getRecipeById(recipe.getRecipeId());
            }
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to the server or database", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new DaoException("Data Integrity Violation", ex);
        }
        return updatedRecipe;
    }
    @Override
    public int deleteRecipe(int id) {
        int deletedRows = 0;
        String sql = "UPDATE recipe SET active= false WHERE recipe_id = ?;";

        try{
            deletedRows = jdbcTemplate.update(sql, id);
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to the server or database", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new DaoException("Data Integrity Violation", ex);
        }

        return deletedRows;
    }

    @Override
    public List<Recipe> getRecipesByIngredientName(String ingredientSearch, boolean wild) {
        List<Recipe> recipes = new ArrayList<>();
        String sql = RECIPE_JOIN_STRING +
                "JOIN recipe_ingredient ri\n" +
                "\tON ri.recipe_id = r.recipe_id\n" +
                "JOIN ingredient i\n" +
                "\tON ri.ingredient_id = i.ingredient_id\n" +
                "WHERE i.name ILIKE ? AND r.active = true;";

        if (wild) {
            ingredientSearch = "%" + ingredientSearch + "%";
        }
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, ingredientSearch);
            while (results.next()) {
                Recipe recipe;
                recipe = mapRowToRecipe(results);
                recipes.add(recipe);
            }
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Cannot connect to server or database", ex);
        }
        return recipes;
    }

    //Optimize this a bit better in the future but glad it works
    @Override
    public List<Recipe> getRecipesByIngredientList(String[] ingredients) {
        List<Recipe> recipes = new ArrayList<>();

        String sql = "SELECT r.recipe_id, r.title, r.ingredients, r.instructions, r.glass, r.account_id, r.rating, r.post_date, r.post_time, count(*) as c FROM recipe r\n" +
                "LEFT JOIN recipe_ingredient ri\n" +
                "\tON r.recipe_id = ri.recipe_id\n" +
                "LEFT JOIN ingredient i\n" +
                "\tON ri.ingredient_id = i.ingredient_id\n";

        String sqlOrder = "GROUP BY r.recipe_id\n" +
                "ORDER BY c DESC, r.rating\n" +
                "LIMIT 100;";

        SqlRowSet results = null;

        List<String> wildIngredients = new ArrayList<>();
        for (String ingredient : ingredients) {
            wildIngredients.add("%" + ingredient + "%");
        }

        switch (wildIngredients.size()) {
            case 1: sql = sql + "WHERE r.active = true AND i.name ILIKE ?\n" + sqlOrder;
                results = jdbcTemplate.queryForRowSet(sql, wildIngredients.get(0));
                while (results.next()) {
                    Recipe recipe;
                    recipe = mapRowToRecipe(results);
                    recipes.add(recipe);
                }
                break;
            case 2: sql = sql + "WHERE r.active = true AND i.name ILIKE ? OR i.name ILIKE ?\n" + sqlOrder;
                results = jdbcTemplate.queryForRowSet(sql, wildIngredients.get(0), wildIngredients.get(1));
                while (results.next()) {
                    Recipe recipe;
                    recipe = mapRowToRecipe(results);
                    recipes.add(recipe);
                }
                break;
            case 3: sql = sql + "WHERE r.active = true AND i.name ILIKE ? OR i.name ILIKE ? OR i.name ILIKE ?\n" + sqlOrder;
                results = jdbcTemplate.queryForRowSet(sql, wildIngredients.get(0), wildIngredients.get(1), wildIngredients.get(2));
                while (results.next()) {
                    Recipe recipe;
                    recipe = mapRowToRecipe(results);
                    recipes.add(recipe);
                }
                break;
            case 4: sql = sql + "WHERE r.active = true AND i.name ILIKE ? OR i.name ILIKE ? OR i.name ILIKE ? OR i.name ILIKE ?\n" + sqlOrder;
                results = jdbcTemplate.queryForRowSet(sql, wildIngredients.get(0), wildIngredients.get(1), wildIngredients.get(2), wildIngredients.get(3));
                while (results.next()) {
                    Recipe recipe;
                    recipe = mapRowToRecipe(results);
                    recipes.add(recipe);
                }
                break;
            case 5: sql = sql + "WHERE r.active = true AND i.name ILIKE ? OR i.name ILIKE ? OR i.name ILIKE ? OR i.name ILIKE ? OR i.name ILIKE ?\n" + sqlOrder;
                results = jdbcTemplate.queryForRowSet(sql, wildIngredients.get(0), wildIngredients.get(1), wildIngredients.get(2), wildIngredients.get(3), wildIngredients.get(4));
                while (results.next()) {
                    Recipe recipe;
                    recipe = mapRowToRecipe(results);
                    recipes.add(recipe);
                }
                break;
            default: sql = sql + sqlOrder;
                results = jdbcTemplate.queryForRowSet(sql);
                while (results.next()) {
                    Recipe recipe;
                    recipe = mapRowToRecipe(results);
                    recipes.add(recipe);
                }
                break;
        }

        return recipes;
    }

    private Recipe mapRowToRecipe(SqlRowSet results) {
        Recipe recipe = new Recipe();
        recipe.setRecipeId(results.getInt("recipe_id"));
        recipe.setTitle(results.getString("title"));
        recipe.setIngredients(results.getString("ingredients"));
        recipe.setInstructions(results.getString("instructions"));
        recipe.setGlass(results.getString("glass"));
        recipe.setAccountId(results.getInt("account_id"));
        recipe.setRating(results.getInt("rating"));
        try{
            recipe.setPostDate(results.getDate("post_date"));
            recipe.setPostTime(results.getTime("post_time"));
        } catch (NullPointerException e) {
            throw new DaoException("Problem converting dates/times", e);
        }

        return recipe;
    }

}
