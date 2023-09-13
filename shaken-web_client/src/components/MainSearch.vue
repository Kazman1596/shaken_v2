<template>
  <div class="home">
    <nav>
      <h3 v-on:click="byRecipe()">By Recipe</h3>
      <h3 v-on:click="byIngredient()">By Ingredient</h3>
    </nav>
    <div v-if="recipeOption" class="search">
      <input v-model="recipeInput" v-on:keyup.enter="searchRecipe()" type="text" placeholder="Search for a recipe" />
    </div>
    <div v-if="ingredientOption" class="search">
      <div id="ingredient-list">
        <div v-for="ingredient in ingredientList" v-bind:id="ingredient">
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

  nav h3 {
    margin: 5%;
    cursor: pointer;
  }

  nav h3:hover {
    color:#626262
  }

  input {
    padding-right: 20%;
    padding-top: 7px;
    padding-bottom: 7px;
    padding-left: 10px;
    font-size: 14px;
    border: solid 1px #0c0c0c;
    border-radius: 8px;
    margin: 10px;
  }

  .search {
    text-align: center;
  }

  #ingredient-list {
    display: flex;
    justify-content: space-around;
  }

  #search-button {
    display: flex;
    justify-content: center;
  }

</style>