package org.example.dao;

import org.example.exception.DaoException;
import org.example.model.Ingredient;
import org.example.model.IngredientRecipeDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcIngredientDao implements IngredientDao {
    private final String INGREDIENT_SELECT_STRING = "SELECT ingredient_id, quantity, unit, name FROM ingredient ";
    private final JdbcTemplate jdbcTemplate;
    public JdbcIngredientDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public List<Ingredient> getIngredients() {
        List<Ingredient> returnedIngredients = new ArrayList<>();
        try {
            String sql = INGREDIENT_SELECT_STRING + ";";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Ingredient ingredient = mapRowToIngredient(results);
                returnedIngredients.add(ingredient);
            }
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        }

        return returnedIngredients;
    }

    @Override
    public List<Ingredient> getIngredientsByRecipe(int recipeId) {
        List<Ingredient> ingredients = new ArrayList<>();
        String sql = "SELECT i.ingredient_id, i.quantity, i.unit, i.name FROM ingredient i \n" +
                "JOIN recipe_ingredient ri \n" +
                "\t ON i.ingredient_id = ri.ingredient_id \n" +
                "JOIN recipe r \n" +
                "\t ON ri.recipe_id = r.recipe_id \n" +
                "WHERE r.recipe_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, recipeId);
            while (results.next()) {
                Ingredient ingredient = mapRowToIngredient(results);
                ingredients.add(ingredient);
            }
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new DaoException("Data Integrity Violation", ex);
        }

        return ingredients;
    }

    @Override
    public Ingredient getIngredientById(int ingredientId) {
        Ingredient ingredient = null;
        String sql = INGREDIENT_SELECT_STRING + "WHERE ingredient_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, ingredientId);
            if (results.next()) {
                ingredient = mapRowToIngredient(results);
            }
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new DaoException("Data Integrity Violation", ex);
        }

        return ingredient;
    }

    @Override
    public Ingredient findIngredient(Ingredient ingredient) {
        Ingredient result = null;
        String sql = INGREDIENT_SELECT_STRING + "WHERE quantity = ? AND unit = ? AND name = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, ingredient.getQuantity(), ingredient.getUnit(), ingredient.getName());
            if (results.next()) {
                result = mapRowToIngredient(results);
            }
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new DaoException("Data Integrity Violation", ex);
        }

        return result;
    }
    //TODO: Test createIngredient, findIngredient, mapIngredientToRecipe
    @Override
    public Ingredient createIngredient(Ingredient newIngredient, int recipeId) {
        Ingredient ingredient = null;
        String sql = "INSERT INTO ingredient(quantity, unit, name) " +
                "VALUES (?, ?, ?) RETURNING ingredient_id;";
        try {
            Ingredient searchedIngredient = findIngredient(newIngredient);
            IngredientRecipeDto ingredientRecipeDto = null;
            if (searchedIngredient == null) {
                int newId = jdbcTemplate.queryForObject(sql, int.class, newIngredient.getQuantity(), newIngredient.getUnit(), newIngredient.getName());
                ingredient = getIngredientById(newId);
                ingredientRecipeDto = new IngredientRecipeDto(recipeId, newId);
            } else {
                ingredientRecipeDto = new IngredientRecipeDto(recipeId, searchedIngredient.getIngredientId());
            }
            mapIngredientToRecipe(ingredientRecipeDto);
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to the server or database", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new DaoException("Data Integrity Violation", ex);
        }
        return ingredient;
    }

    @Override
    public Ingredient updateIngredient(Ingredient updatedIngredient) {
        Ingredient ingredient = null;
        String sql = "UPDATE ingredient SET quantity=?, unit=?, name=? WHERE ingredient_id = ?;";
        try {
            int rowCount = jdbcTemplate.update(sql, updatedIngredient.getQuantity(), updatedIngredient.getUnit(), updatedIngredient.getName(), updatedIngredient.getIngredientId());
            if (rowCount == 0) {
                throw new DaoException("Expected 1 updated ingredient, but found 0");
            } else {
                ingredient = getIngredientById(updatedIngredient.getIngredientId());
            }
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to the server or database", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new DaoException("Data Integrity Violation", ex);
        }
        return ingredient;
    }

    @Override
    public int removeIngredientFromRecipe(int ingredientId, int recipeId) {
        int deletedRows = 0;

        String sql = "DELETE FROM recipe_ingredient WHERE ingredient_id = ? AND recipe_id = ?;";

        try{
            deletedRows = jdbcTemplate.update(sql, ingredientId, recipeId);
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to the server or database", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new DaoException("Data Integrity Violation", ex);
        }

        return deletedRows;
    }

    @Override
    public void mapIngredientToRecipe(IngredientRecipeDto ingredientRecipeDto) {
        String sql = "INSERT INTO recipe_ingredient(recipe_id, ingredient_id) " +
                "VALUES (?, ?) RETURNING recipe_id;";
        try {
            int recipeId = jdbcTemplate.queryForObject(sql, int.class, ingredientRecipeDto.getRecipeId(), ingredientRecipeDto.getIngredientId());
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to the server or database", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new DaoException("Data Integrity Violation", ex);
        }
    }

    private Ingredient mapRowToIngredient(SqlRowSet results) {
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientId(results.getInt("ingredient_id"));
        ingredient.setQuantity(results.getString("quantity"));
        ingredient.setUnit(results.getString("unit"));
        ingredient.setName(results.getString("name"));

        return ingredient;
    }
}
