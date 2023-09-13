<template>
    <div>
        <div class="recipe-list" v-for="recipe in results" v-bind:key="recipe.id">
            <RecipeCard :recipe="recipe"/>
        </div>
    </div>
  </template>
  
  <script>
    import RecipeCard from '../components/RecipeCard.vue';
  import RecipeService from '../services/RecipeService';

  export default {
    name: "searchResults",
    components: {RecipeCard},
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
  