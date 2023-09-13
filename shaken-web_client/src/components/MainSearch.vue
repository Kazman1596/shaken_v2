<template>
  <div class="home">
    <nav>
      <h3 v-bind:class="(recipeOption) ? 'option selected' : 'option'" v-on:click="byRecipe()">By Recipe</h3>
      <h3 v-bind:class="(ingredientOption) ? 'option selected' : 'option'" v-on:click="byIngredient()">By Ingredient</h3>
    </nav>
    <div v-if="recipeOption" class="search">
      <input v-model="recipeInput" v-on:keyup.enter="searchRecipe()" type="text" placeholder="Search for a recipe" />
    </div>
    <div v-if="ingredientOption" class="search">
      <div id="ingredient-list">
        <div class="ingredient" v-for="ingredient in ingredientList" v-bind:id="ingredient">
          <p>{{ ingredient }}</p>
        </div>
      </div>
      <input v-model="ingredientInput" v-on:keyup.enter="addIngredient()" type="text" placeholder="Add an ingredient" />
    </div>
    <div id="search-button">
      <button v-show="ingredientList.length > 0" v-on:click="searchIngredients()">Search for Recipes</button>
    </div>
  </div>
</template>
  
<script>
  export default {
    name: "home",
    data() {
      return {
        recipeInput: "",
        ingredientInput: "",
        ingredientList: [],
        recipeOption: true,
        ingredientOption: false
      }
    },
    methods: {
      byRecipe() {
        this.recipeOption = true;
        this.ingredientOption = false;
      },
      byIngredient() {
        this.recipeOption = false;
        this.ingredientOption = true;
      },
      searchRecipe() {
        const route = {
          name: 'searchRecipe',
          params: { input: this.recipeInput }
        }
  
        this.$router.push(route)
        this.recipeInput = "";
      },
      addIngredient() {
        this.ingredientList.push(this.ingredientInput)
        this.ingredientInput = "";
      },
      searchIngredients() {
        const queryparams = this.ingredientList.join("&")
        const route = {
          name: 'searchIngredients',
          params: { ingredients: queryparams }
        }

        this.$router.push(route)
        this.ingredientInput = "";
      }
    }
  };
</script>

<style scoped>

  nav {
    display: flex;
    justify-content: center;
  }

  .option {
    margin: 5%;
    cursor: pointer;
    border-bottom: solid 1px transparent;
  }

  .option:hover {
    color:#00eeff;
    transition-duration: 250ms;
  }

  .option:not(:hover) {
    transition-duration: 250ms;
  }

  .selected {
    border-bottom: solid 1px #00eeff;
    color: #00eeff;
  }

  input {
    padding-right: 20%;
    padding-top: 7px;
    padding-bottom: 7px;
    padding-left: 10px;
    font-size: 14px;
    border: solid 1px #ffffff;
    border-radius: 8px;
    margin: 10px;
  }

  ::placeholder {
    color: #d3d3d3;
  }

  .search {
    text-align: center;
  }

  #ingredient-list {
    display: flex;
    justify-content: center;
  }

  .ingredient {
    margin: 25px;
  }

  .ingredient p {
    border: solid 1px #ffaa00;
    border-radius: 10px;
    min-width: 100px;
    padding: 5px;
    animation: fadeIn s;
  }

  @keyframes fadeIn {
    0% {opacity: 0;}
    100% {opacity: 1;}
  }

  #search-button {
    display: flex;
    justify-content: center;
    margin: 15px;
  }

  #search-button button {
    border: solid 1px #ffaa00;
    border-radius: 10px;
    padding: 6px;
    cursor: pointer;
  }

  #search-button button:hover {
    border: solid 1px #00eeff;
    color: #00eeff;
    transition-duration: 250ms;
  }

  #search-button button:not(:hover) {
    transition-duration: 250ms;
  }

</style>