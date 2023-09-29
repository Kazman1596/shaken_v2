<template>
  <div id="recipe-list">
    <p id="num-results">{{ numResults }}</p>
      <div v-for="recipe in results" v-bind:key="recipe.id">
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
    this.searchRecipe();
  },
  computed: {
    numResults() {
      this.searchRecipe();
      return this.results.length + " results";
    }
  },
  methods: {
    searchRecipe() {
      RecipeService.searchRecipe(this.$route.params.input).then((response) => {
        this.results = response.data;
      })
    }
  }
};
</script>

<style scoped>
  #num-results {
    margin-left: 10%;
  }
</style>
  