<template>
    <div id="create-form">
        <h2>Create Recipe</h2>
        <input required v-model="newRecipe.title" type="text" class="input-section" id="title" placeholder="Title" />
        <div id="ingredient-list">
            <div id="added-ingredients" v-for="ingredient in newIngredients" v-bind:id="ingredient.name">
                <div id="ingredient-details">
                    <p>{{ ingredient.quantity }}</p>
                    <p id="unit">{{ ingredient.unit }}</p>
                    <p>{{ ingredient.name }}</p>
                </div>
                <button v-on:click="removeIngredient(ingredient)">Remove</button>
            </div>
        </div>
        <div id="ingredient" class="input-section">
            <input required v-model="ingredientQuantity" type="number" id="quantity" placeholder="Quantity" />
            <div>
                <select v-model="ingredientUnit" id="measurement">
                    <option selected disabled value>Measurement</option>
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
            </div>
            <input required v-model="ingredientName" type="text" class="input-section" placeholder="Ingredient" />
            <button v-on:click="addIngredient()">Add</button>
        </div>
        <textarea required v-model="newRecipe.instructions" type="text" id="instructions" class="input-section" placeholder="Instructions" />
        <select required v-model="newRecipe.glass" id="glass" placeholder="Glassware">
            <option disabled selected value>Glassware</option>
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
        <div>
            <button v-on:click="createRecipe()">Create Recipe</button>
        </div>
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

            const route = {
                name: "home"
            }

            this.$router.push(route);
        },
        addIngredient() {
            const newIngredient = {
                quantity: this.ingredientQuantity,
                unit: this.ingredientUnit,
                name: this.ingredientName.toLowerCase(),
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

    #create-form {
        display: flex;
        flex-direction: column;
        align-items: center;
        text-align: center;
        border: 1px solid #00eeff;
        border-radius: 15px;
        margin: 100px 10%;
        padding: 10px;
    }

    #ingredient-list {
        display: flex;
        justify-content: space-evenly;
    }

    #ingredient-details {
        display: flex;
        border: solid 1px #ffaa00;
        align-items: center;
        border-radius: 10px;
        min-width: 100px;
        padding: 1px;
        animation: fadeIn 500ms;
        margin: 5px;
    }

    #ingredient-details p {
        margin: 3px;
        padding: 2px;
    }

    @keyframes fadeIn {
        0% {opacity: 0;}
        100% {opacity: 1;}
    }

    #ingredient {
        display: flex;
        justify-content: center;
        align-items: center;

    }

    .form-input-group {
        margin-bottom: 1rem;
    }
    
    label {
        margin-right: 0.5rem;
    }
    
    button {
        border: solid 1px #ffaa00;
        border-radius: 10px;
        padding: 6px;
        margin: 5px;
        cursor: pointer;
    }
    
    button:hover {
        border: solid 1px #00eeff;
        color: #00eeff;
        transition-duration: 250ms;
    }
    
    button:not(:hover) {
        transition-duration: 250ms;
    }
    
    input {
        padding-right: 10%;
        padding-top: 7px;
        padding-bottom: 7px;
        padding-left: 10px;
        font-size: 14px;
        border: solid 1px #ffffff;
        border-radius: 8px;
        margin: 10px;
    }

    select {
        border-radius: 5px;
        margin: 10px 5px;
    }

    #quantity {
        padding-right: 15px;
    }

    #instructions {
        border: solid 1px #ffffff;
    }

    #unit {
        font-size: 12px;
    }

    ::placeholder {
    color: #d3d3d3;
    }

</style>