<template>
    <div id="review-card">
        <p>{{ review.title }}</p>
        <p>{{ review.userId }}</p>
        <p>{{ review.postDate }}</p>
        <p>{{ review.postTime }}</p>
        <p>{{ review.rating }} / 5</p>
        <p>{{ review.description }}</p>
    </div>
  </template>
    
  <script>
  
  export default {
    name: "review",
    props: {
        review: {}
    }
  };
  </script>
  
  <style scoped>
    #review-card {
        border: 1px solid #00eeff;
        border-radius: 10px;
    }
  </style>