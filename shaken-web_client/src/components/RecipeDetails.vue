<template>
    <div>
        <h1>{{ recipe.title }}</h1>
        <div v-for="ingredient in ingredients" v-bind:key="ingredient.id">
            <Ingredient :ingredient="ingredient" />
        </div>
        <p>{{ recipe.instructions }}</p>
    </div>
  </template>
  
  <script>
    import ingredientService from '../services/IngredientService';
    import recipeService from '../services/RecipeService';
    import Ingredient from '../components/Ingredient.vue'
    export default {
        name: "recipe-details",
        components: {Ingredient},
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