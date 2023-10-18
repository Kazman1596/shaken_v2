<template>
    <div id="ingredient">
        <p>{{ ingredient.quantity }}</p>
        <p id="measurement">{{ ingredient.measurement }}</p>
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
        border: 1px solid #ffaa00;
        border-radius: 10px;
    }

    #ingredient p {
        margin: 5px;
    }

    #measurement {
        font-size: 12px;
    }
  </style>