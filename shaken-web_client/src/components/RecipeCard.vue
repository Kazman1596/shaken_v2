<template>
    <div id="card">
        <div id="top">
            <h5>{{ recipe.rating }} Stars</h5>
            <h3 id="title">{{ recipe.title }}</h3>
            <div id="created-by">
                <router-link v-bind:to="{name: 'userProfile', params: {username: $store.state.user.username}}">{{ user.username }}</router-link>
                <img class="profile-pic" v-bind:src="user.profilePicture" />
            </div>
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
    import accountService from '../services/AccountService';

    export default {
    name: "searchResults",
    props: {
        recipe: {}
    },
    data() {
        return {
        ingredients: "",
        results: [],
        user: {}
        }
    },
    created() {
        accountService.getAccountById(this.recipe.accountId).then((response => {
            this.user = response.data
        }))
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
    }

    #body {
        display: flex;
    }

    #info {
        margin-left: 15px;
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
        cursor: pointer;
        border-bottom: 1px solid transparent;
    }

    a:hover {
        border-bottom: 1px solid #ffaa00;
        transition-duration: 250ms;
    }

    a:not(:hover) {
        transition-duration: 250ms;
    }

</style>