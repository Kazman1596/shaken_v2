<template>
    <div id="ingredient">
        <p>{{ ingredient.quantity }}</p>
        <p id="unit">{{ ingredient.unit }}</p>
        <p>{{ ingredient.name }}</p>
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