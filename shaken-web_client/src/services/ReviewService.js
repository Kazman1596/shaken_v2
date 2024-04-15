import axios from 'axios';

export default {

  getReviewsByRecipe(id) {
    return axios.get(`/reviews/recipe/${id}`)
  }
  
}