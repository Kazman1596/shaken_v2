<template>
    <div>
        <div v-for="recipe in myCocktails" v-bind:id="recipe.id">
          <RecipeCard :recipe="recipe"/>
        </div>
    </div>
  </template>
  
  <script>
  import recipeService from '../services/RecipeService';
  import RecipeCard from './RecipeCard.vue';
  export default {
    name: "myCocktails",
    components: { RecipeCard },
    data() {
      return{
        myCocktails: [],
      }
    },
    created() {
      recipeService.getRecipesByAccount(this.$store.state.user.id).then((response) => {
        this.myCocktails = response.data;
      })
    },
    methods: {
      
    }
  };
  </script>
  
  
  <style scoped>
  
  </style>