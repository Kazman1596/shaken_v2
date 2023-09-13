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
        searchInput: "",
        results: []
      }
    },
    created() {
        RecipeService.searchRecipe(this.$route.params.input).then((response) => {
            this.results = response.data;
        })
    },
    methods: {
      searchRecipe() {
        const route = {
          name: 'searchRecipe',
          params: { input: this.searchInput }
        }
  
        this.$router.push(route)
        this.searchInput = "";
      }
    }
  };
  </script>
  