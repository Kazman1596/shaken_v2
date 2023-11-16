<template>
    <div>
        <h2>Create Recipe</h2>
        <input v-model="newRecipe.title" type="text" id="title" placeholder="Title" />
        <div id="ingredient">
            <input v-model="newIngredient.quantity" type="number" id="quantity" placeholder="Quantity" />
            <select v-model="newIngredient.unit" id="unit">
                <option>N/A</option>
                <option value="ounce">ounce</option>
                <option value="drop">drop</option>
                <option value="pinch">pinch</option>
                <option value="tablespoon">tablespoon</option>
                <option value="teaspoon">teaspoon</option>
                <option value="cup">cup</option>
                <option value="scoop">scoop</option>
                <option value="dash">dash</option>
            </select>
            <input v-model="newIngredient.name" type="text" id="name" placeholder="Name" />
            <button v-on="addIngredient()">Add Ingredient</button>
        </div>
        <textarea v-model="newRecipe.instructions" type="text" id="instructions" placeholder="Instructions" />
        <select v-model="newRecipe.glass" id="glass" placeholder="Glassware">
            <option value="Old-Fashioned Glass">Old-Fashioned Glass</option>
            <option value="Cocktail Glass">Cocktail Glass</option>
            <option value="Shot Glass">Shot Glass</option>
            <option value="Mixing Glass">Mixing Glass</option>
            <option value="Goblet">Goblet</option>
            <option value="Beer Mug">Beer Mug</option>
            <option value="Tankard">Tankard</option>
            <option value="Collins Glass">Collins Glass</option>
            <option value="Wine Glass">Wine Glass</option>
            <option value="Irish Coffee Glass">Irish Coffee Glass</option>
            <option value="Sour Glass">Sour Glass</option>
            <option value="Cafe Glass">Cafe Glass</option>
            <option value="Snifter">Snifter</option>
            <option value="Highball Glass">Highball Glass</option>
            <option value="Champagne Flute">Champagne Flute</option>
        </select>
        <button v-on:click="createRecipe()">Create Recipe</button>
    </div>
  </template>
  
  <script>
  export default {
    name: "add-cocktail",
    components: {},
    data() {
      return{
        newIngredient: {
            quantity: 0,
            unit: "",
            name: ""
        },
        newRecipe: {
            title: "",
            ingredients: [],
            instructions: "",
            glass: "",
            accountId: ""
        }
      }
    },
    methods: {
        createRecipe() {
            // Create a new recipe
            // Send to API
            // Send user home
        },
        addIngredient() {
            // Create a new ingredient
            // Add it to newRecipe.ingredients array
        }
    }
  };
  </script>
  
  
  <style scoped>

  </style>