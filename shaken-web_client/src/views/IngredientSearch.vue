<template>
    <div>
        <div class="recipe-list" v-for="recipe in results" v-bind:key="recipe.id">
          <RecipeCard :recipe="recipe"/>
        </div>
    </div>
  </template>
  
  <script>
    import RecipeService from '../services/RecipeService';
    import RecipeCard from '../components/RecipeCard.vue';

  export default {
    name: "searchResults",
    components: { RecipeCard },
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