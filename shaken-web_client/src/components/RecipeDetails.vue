<template>
    <div>
        <h1>{{ recipe.title }}</h1>
        <div v-for="ingredient in ingredients" v-bind:key="ingredient.id">
            <Ingredient :ingredient="ingredient" />
        </div>
        <p>{{ recipe.instructions }}</p>
        <div id="review-section">
            <h2>Reviews</h2>
            <div id="reviews">
                <ReviewCard />
            </div>
        </div>
    </div>
  </template>
  
  <script>
    import ingredientService from '../services/IngredientService';
    import recipeService from '../services/RecipeService';
    import Ingredient from '../components/Ingredient.vue';
    import ReviewCard from '../components/ReviewCard.vue';
    export default {
        name: "recipe-details",
        components: {Ingredient, ReviewCard},
        props: {
            recipe: "recipe"
        },
        data() {
            return {
            ingredients: [],
            recipe: {},
            }
        },
        created() {
            recipeService.getRecipeById(this.$route.params.recipeId).then((response) => {
                this.recipe = response.data;
            })

            ingredientService.getIngredientsByRecipe(this.$route.params.recipeId).then((response) => {
                this.ingredients = response.data;
            })
        }
    }
  </script>