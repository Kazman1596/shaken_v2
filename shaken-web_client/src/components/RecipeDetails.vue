<template>
    <div>
        <h1>{{ recipe.title }}</h1>
        <div v-for="ingredient in ingredients" v-bind:key="ingredient.id">
            <Ingredient :ingredient="ingredient" />
        </div>
        <p>{{ recipe.instructions }}</p>
        <div id="review-section">
            <h2>Reviews</h2>
            <div v-for="review in reviews" v-bind:key="review.id" id="reviews">
                <ReviewCard :review="review" />
            </div>
        </div>
    </div>
  </template>
  
  <script>
    import ingredientService from '../services/IngredientService';
    import recipeService from '../services/RecipeService';
    import reviewService from '../services/ReviewService';
    import Ingredient from '../components/Ingredient.vue';
    import ReviewCard from '../components/ReviewCard.vue';
    
    export default {
        name: "recipe-details",
        components: {Ingredient, ReviewCard},
        data() {
            return {
            ingredients: [],
            recipe: {},
            reviews: []
            }
        },
        created() {
            recipeService.getRecipeById(this.$route.params.recipeId).then((response) => {
                this.recipe = response.data;
            })

            ingredientService.getIngredientsByRecipe(this.$route.params.recipeId).then((response) => {
                this.ingredients = response.data;
            })

            reviewService.getReviewsByRecipe(this.$route.params.recipeId).then((response) => {
                this.reviews = response.data
            })
        }
    }
  </script>