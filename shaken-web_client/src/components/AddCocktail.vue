<template>
    <div>
        <h2>Create Recipe</h2>
        <input v-model="newRecipe.title" type="text" id="title" placeholder="Title" />
        <div id="added-ingredients" v-for="ingredient in newIngredients" v-bind:id="ingredient.name">
            <p>{{ ingredient.quantity }}</p>
            <p>{{ ingredient.unit }}</p>
            <p>{{ ingredient.name }}</p>
            <button v-on:click="removeIngredient(ingredient)">Remove</button>
        </div>
        <div id="ingredient">
            <input v-model="ingredientQuantity" type="number" id="quantity" placeholder="Quantity" />
            <select v-model="ingredientUnit" id="unit">
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
            <input v-model="ingredientName" type="text" id="name" placeholder="Name" />
            <button v-on:click="addIngredient()">Add Ingredient</button>
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
    import recipeService from "../services/RecipeService"
    import ingredientService from "../services/IngredientService"

  export default {
    name: "add-cocktail",
    components: {},
    data() {
      return{
        ingredientQuantity: 0,
        ingredientUnit: "",
        ingredientName: "",
        newRecipe: {
            title: "",
            instructions: "",
            glass: "",
            accountId: this.$store.state.user.id,
        },
        newIngredients: [],
      }
    },
    methods: {
        createRecipe() {
            // Create a new recipe
            recipeService.createRecipe(this.newRecipe).then((response) => {
                console.log(response.data)
                // Create or map new ingredients
                this.newIngredients.forEach((ingredient => {
                    ingredientService.createIngredient(ingredient, response.data.recipeId)
                }))
            })

            // Send to API
            // Send user home
        },
        addIngredient() {
            const newIngredient = {
                quantity: this.ingredientQuantity,
                unit: this.ingredientUnit,
                name: this.ingredientName,
            }
            this.newIngredients.push(newIngredient);
            this.ingredientQuantity = 0;
            this.ingredientUnit = "";
            this.ingredientName = "";
        },
        removeIngredient(ingredient) {
            const i = this.newIngredients.indexOf(ingredient)

            this.newIngredients.splice(i, 1)
        }
    }
  };
  </script>
  
  
  <style scoped>

  </style>