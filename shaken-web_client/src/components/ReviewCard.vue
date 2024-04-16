<template>
    <div id="review-card">
      <div id="review-heading">
        <div class="section">
          <img v-bind:src="this.user.profilePicture" />
          <p>{{ this.user.username }}</p>
          <p>{{ review.rating }} / 5</p>
        </div>
        <div class="section">
          <p>{{ review.postDate }}</p>
          <p>{{ review.postTime }}</p>
        </div>
      </div>
        <div id="review-body">
          <p>{{ review.description }}</p>
        </div>
    </div>
  </template>
    
  <script>
  import accountService from "../services/AccountService"
  
  export default {
    name: "review",
    props: {
        review: {}
    },
    data() {
      return {
        user: {}
    }
    },
    created() {
      accountService.getAccountById(this.review.userId).then((response) => {
        this.user = response.data;
      })
    }
  };
  </script>
  
  <style scoped>
    #review-card {
        border: 1px solid #00eeff;
        border-radius: 10px;
        padding: 15px;
        margin: 20px 10px;
    }

    #review-heading {
      display: flex;
      justify-content:space-between;
    }

    .section {
      display: flex;
      align-items: center;
    }

    .section p{
      margin: 10px;
    }

    img {
        height: 45px;
        border-radius: 50%;
        margin: 10px;
    }
  </style>