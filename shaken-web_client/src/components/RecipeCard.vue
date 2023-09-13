<template>
    <div id="card">
        <div id="top">
            <h5>{{ recipe.rating }} Stars</h5>
            <h3 id="title">{{ recipe.title }}</h3>
            <h5>User {{ recipe.accountId }}</h5>
        </div>
        <div id="body">
            <h4 id="glass">{{ recipe.glass }}</h4>
            <div id="info">
                <p id="ingredients">{{ recipe.ingredients }}</p>
                <p id="instructions">{{ recipe.instructions }}</p>
            </div>
        </div>
    </div>
</template>

<script>

    export default {
    name: "searchResults",
    props: {
        recipe: {}
    },
    data() {
        return {
        ingredients: "",
        results: []
        }
    }
};
</script>

<style>
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
    }

    #body {
        display: flex;
    }

    #info {
        margin-left: 15px;
    }



</style>