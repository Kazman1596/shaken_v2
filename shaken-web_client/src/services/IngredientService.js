import axios from 'axios';

export default {

  getIngredientsByRecipe(id) {
    return axios.get(`/ingredients/recipe/${id}`)
  }

}