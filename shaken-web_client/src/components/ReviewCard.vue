<template>
    <div id="review-card">
        <p>This is a good review.</p>
        <p>kazman1596</p>
        <p>great idea!</p>
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