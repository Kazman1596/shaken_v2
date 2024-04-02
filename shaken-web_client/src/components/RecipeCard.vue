<template>
    <div id="card">
        <div id="top">
            <h5>{{ recipe.rating }} Stars</h5>
            <router-link v-bind:to="{name: 'recipe', params: {recipeId: recipe.recipeId}}"><h3 id="title">{{ recipe.title }}</h3></router-link>
            <div id="created-by">
                <router-link v-bind:to="{name: 'userProfile', params: {username: $store.state.user.username}}">{{ user.username }}</router-link>
                <img class="profile-pic" v-bind:src="user.profilePicture" />
            </div>
        </div>
        <div id="body">
            <h4 id="glass">{{ recipe.glass }}</h4>
            <div id="info">
                <!-- <p>{{ recipe.ingredients }}</p> -->
                <div id="ingredients">
                    <div class="ingredient" v-for="ingredient in ingredients" v-bind:id="ingredient.id">
                       <Ingredient :ingredient="ingredient"/>
                    </div>
                </div>
                <p id="instructions">{{ recipe.instructions }}</p>
            </div>
        </div>
        <div id="user-permissions">
            <p v-on:click="editRecipe()" class="button" id="edit-button">Edit</p>
            <p v-on:click="deleteRecipe()" class="button" id="delete-button">Delete</p>
        </div>
    </div>
</template>

<script>
    import accountService from '../services/AccountService';
    import ingredientService from '../services/IngredientService';
    import Ingredient from './Ingredient.vue';

    export default {
    name: "searchResults",
    components: {Ingredient},
    props: {
        recipe: {}
    },
    data() {
        return {
        ingredients: {},
        results: [],
        user: {}
        }
    },
    created() {
        accountService.getAccountById(this.recipe.accountId).then((response => {
            this.user = response.data
        }))

        ingredientService.getIngredientsByRecipe(this.recipe.recipeId).then((response => {
            this.ingredients = response.data
        }))
    },
    methods: {
        editRecipe() {
            const route = {
                name: "editCocktail",
                params: {recipeId: this.recipe.recipeId}
            }
            this.$router.push(route)
        },

        deleteRecipe() {
            console.log("delete recipe")
        }
    }
};
</script>

<style scoped>
    #card {
        margin: auto 10%;
        padding-bottom: 2%;
        border-bottom: solid 1px #00eeff;
    }

    #top {
        display: flex;
        justify-content: space-between;
    }

    #title {
        text-align: center;
        border-bottom: 1px solid transparent;
        margin-left: 100px;
    }

    #body {
        display: flex;
    }

    #info {
        margin-left: 15px;
        min-width: 95%;
    }

    #ingredients {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        margin: auto 100px;
        padding-bottom: 10px;
        border-bottom: 1px solid #616161;
    }

    .ingredient {
        padding-right: 15px;
        padding-left: 15px;
        border-right: 1px solid #ffaa00;
    }

    .ingredient:last-child {
        border: none;
    }

    #instructions {
        text-align: center;
    }

    #created-by {
        display: flex;
        align-items: center;
    }

    .profile-pic {
        height: 45px;
        border-radius: 50%;
        margin: 10px;
    }

    a{
        text-decoration: none;
        font-size: 14px;
        border-bottom: 1px solid transparent;
    }

    #title:hover {
        border-bottom: 1px solid #ffaa00;
        transition-duration: 250ms;
        cursor: pointer;
    }

    #title:not(:hover) {
        transition-duration: 250ms;
    }

    #user-permissions {
        display: flex;
        justify-content: right;
    }

    .button {
        margin: 5px;
        font-size: 12px;
        border: none;
    }

    .button:hover {
        cursor: pointer;
        transition-duration: 250ms;
    }

    #edit-button:hover {
        color:#00eeff;
    }

    #delete-button:hover {
        color:#ffaa00;
    }

    .button:not(:hover) {
        transition-duration: 250ms;
    }

</style>