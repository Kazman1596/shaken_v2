<template>
    <div id="review-card">
        <p>Review</p>
        <p>kazman1596</p>
        <p>great idea!</p>
    </div>
  </template>
    
  <script>
  
  export default {
    name: "ingredient",
    props: {
        ingredient: {}
    }
  };
  </script>
  
  <style scoped>
    #ingredient {
        display: flex;
        align-items: baseline;
    }

    #ingredient p {
        margin: 5px;
    }

    #unit {
        font-size: 12px;
    }
  </style>