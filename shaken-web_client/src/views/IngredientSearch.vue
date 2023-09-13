<template>
    <div>
        <div class="recipe-list" v-for="recipe in results" v-bind:key="recipe.id">
            <h3>{{ recipe.title }}</h3>
            <h4>{{ recipe.glass }}</h4>
            <p>{{ recipe.ingredients }}</p>
            <p>{{ recipe.instructions }}</p>
        </div>
    </div>
  </template>
  
  <script>
    import RecipeService from '../services/RecipeService';

  export default {
    name: "searchResults",
    data() {
      return {
        ingredients: "",
        results: []
      }
    },
    created() {
        this.ingredients = this.$route.params.ingredients.split("&")
        RecipeService.searchIngredients(this.ingredients).then((response) => {
            this.results = response.data;
        })
    }
  };
  </script>