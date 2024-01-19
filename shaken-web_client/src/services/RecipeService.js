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
  },

  getRecipesByAccount(accountId) {
    return axios.get(`/recipes/user/${accountId}`)
  },

  createRecipe(recipe) {
    return axios.post('/recipes', recipe)
  },

  //not finished yet, will not work
  editRecipe(id) {
    return axios.put(`/recipes/${id}`)
  },

  deleteRecipe(id) {
    return axios.delete(`recipes/${id}`)
  }
}