<template>
    <div>
      <p id="num-results">{{ numResults }}</p>
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
        this.searchIngredients();
    },
    computed: {
      numResults() {
        this.searchIngredients();
        return this.results.length + " results";
      }
    },
    methods: {
      searchIngredients() {
        this.ingredients = this.$route.params.ingredients.split("&")
        RecipeService.searchIngredients(this.ingredients).then((response) => {
            this.results = response.data;
        })
      }
    }
  };
  </script>

<style>
  #num-results {
    margin-left: 10%;
  }

</style>