import org.example.dao.JdbcIngredientDao;
import org.example.model.Ingredient;
import org.example.model.IngredientRecipeDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class JdbsIngredientDaoTests extends BaseDaoTests {

    private Ingredient matchingIngredient = new Ingredient(10, "1", "ounce", "lime");
    private Ingredient testIngredient;
    private JdbcIngredientDao sut;

    @Before
    public void setup() throws SQLException {
        sut = new JdbcIngredientDao(dataSource);
        testIngredient = new Ingredient(17, "2 1/2", "ounce", "test ingredient");
    }

    @Test
    public void getIngredients_returns_correctly() {
        List<Ingredient> results = sut.getIngredients();

        Assert.assertEquals(results.size(), 7);
    }

    @Test
    public void getIngredientsByRecipe_returns_correctly() {
        List<Ingredient> results = sut.getIngredientsByRecipe(1);
        Assert.assertEquals(results.size(), 3);
    }

    @Test
    public void getIngredientsByRecipe_returns_empty_if_null_recipe() {
        List<Ingredient> results = sut.getIngredientsByRecipe(99);
        Assert.assertEquals(results.size(), 0);
    }

    @Test
    public void getIngredientById_returns_correct_ingredient() {
        Ingredient result = sut.getIngredientById(10);
        assertIngredientsMatch(result, matchingIngredient);
    }

    @Test
    public void getIngredientById_returns_null_if_doesnt_exist() {
        Ingredient result = sut.getIngredientById(99);
        Assert.assertNull(result);
    }

    @Test
    public void createIngredient_returns_new_ingredient() {
        Ingredient newIngredient = sut.createIngredient(testIngredient);

        int newId = newIngredient.getIngredientId();
        Assert.assertTrue(newId > 0);

        Ingredient retrievedIngredient = sut.getIngredientById(newId);
        assertIngredientsMatch(newIngredient, retrievedIngredient);
    }

    @Test
    public void updatedIngredient_returns_correct_results() {
        Ingredient ingredientToUpdate = sut.getIngredientById(10);
        ingredientToUpdate.setMeasurement("spoonfuls");
        ingredientToUpdate.setQuantity("18");
        ingredientToUpdate.setName("tomato bisque");
        sut.updateIngredient(ingredientToUpdate);

        Ingredient retrievedIngredient = sut.getIngredientById(10);

        assertIngredientsMatch(ingredientToUpdate, retrievedIngredient);
    }

    @Test
    public void mapIngredient_and_removeIngredient_from_recipe() {
        IngredientRecipeDto newDto = new IngredientRecipeDto(1, 14);

        sut.mapIngredientToRecipe(newDto);
        List<Ingredient> addedIngredientsFromRecipe = sut.getIngredientsByRecipe(1);
        Assert.assertEquals(addedIngredientsFromRecipe.size(), 4);

        sut.removeIngredientFromRecipe(14, 1);
        List<Ingredient> removedIngredientsFromRecipe = sut.getIngredientsByRecipe(1);
        Assert.assertEquals(removedIngredientsFromRecipe.size(), 3);
    }

    private void assertIngredientsMatch(Ingredient expected, Ingredient result) {
        Assert.assertEquals(expected.getIngredientId(), result.getIngredientId());
        Assert.assertEquals(expected.getQuantity(), result.getQuantity());
        Assert.assertEquals(expected.getMeasurement(), result.getMeasurement());
        Assert.assertEquals(expected.getName(), result.getName());
    }

}
