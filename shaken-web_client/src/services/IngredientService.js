import axios from 'axios';

export default {

  getIngredientsByRecipe(id) {
    return axios.get(`/ingredients/recipe/${id}`)
  },

  createIngredient(ingredient, recipeId) {
    return axios.post(`/ingredients/recipe/${recipeId}`, ingredient)
  }

}