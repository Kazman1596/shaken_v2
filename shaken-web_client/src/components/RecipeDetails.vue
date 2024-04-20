<template>
    <div>
        <h1>{{ recipe.title }}</h1>
        <div v-for="ingredient in ingredients" v-bind:key="ingredient.id">
            <Ingredient :ingredient="ingredient" />
        </div>
        <p>{{ recipe.instructions }}</p>
        <div id="review-section">
            <div id="review-header">
                <h2>Reviews</h2>
                <button v-on:click="writeReview = !writeReview">Write a review</button>
            </div>
            <div v-if="writeReview" id="create-review">
                <div id="review-form">
                    <input class="input" type="text" placeholder="Title"/>
                    <textarea class="input" placeholder="Say something..."/>
                </div>
                <button>Submit</button>
            </div>
            <div v-for="review in reviews" v-bind:key="review.id" id="reviews">
                <ReviewCard :review="review" />
            </div>
        </div>
    </div>
  </template>
  
  <script>
    import ingredientService from '../services/IngredientService';
    import recipeService from '../services/RecipeService';
    import reviewService from '../services/ReviewService';
    import Ingredient from '../components/Ingredient.vue';
    import ReviewCard from '../components/ReviewCard.vue';
    
    export default {
        name: "recipe-details",
        components: {Ingredient, ReviewCard},
        data() {
            return {
            ingredients: [],
            recipe: {},
            reviews: [],
            writeReview: false
            }
        },
        created() {
            recipeService.getRecipeById(this.$route.params.recipeId).then((response) => {
                this.recipe = response.data;
            })

            ingredientService.getIngredientsByRecipe(this.$route.params.recipeId).then((response) => {
                this.ingredients = response.data;
            })

            reviewService.getReviewsByRecipe(this.$route.params.recipeId).then((response) => {
                this.reviews = response.data
            })
        },
        methods: {
            createReview() {
                console.log("Writing a new review")
            }
        }
    }
</script>

<style>

    #create-review {
        border: 1px solid #ffaa00;
        border-radius: 15px;
        margin: 15px 350px;
        padding: 20px;
    }

    #review-form {
        display: flex;
        flex-direction: column;
    }

    .input {
        margin: 10px;
    }


</style>