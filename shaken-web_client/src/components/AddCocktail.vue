<template>
    <div>
        <h2>Create Recipe</h2>
        <form>
            <input type="text" id="title" placeholder="Title" />
            <input type="text" id="ingredients" placeholder="Ingredients" />
            <input type="text" id="instructions" placeholder="Instructions" />
            <select id="glass" placeholder="Glassware">
                <option value="old-fashioned glass">Rocks Glass</option>
            </select>
            <button type="submit">Create Recipe</button>
        </form>
    </div>
  </template>
  
  <script>
  export default {
    name: "add-cocktail",
    components: {},
    data() {
      return{
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
      
    }
  };
  </script>
  
  
  <style scoped>

  </style>