import axios from 'axios';

export default {

  searchRecipe(title) {
    return axios.get(`/recipes?title=${title}`)
  },

  searchIngredients(ingredientsArray) {
    const query = ingredientsArray.join(',')
    return axios.get(`/recipes/search?ingredients=${query}`)
  },

  getRecipeById(id) {
    return axios.get(`/recipes/${id}`)
  }

}