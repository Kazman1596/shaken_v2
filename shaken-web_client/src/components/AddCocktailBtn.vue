<template>
    <div id="add-button">
    <PlusCircleIcon class="btn add" @mouseover="hover = true" @mouseleave="hover = false" v-if="!hover" />
    <PlusSolid class="btn add-hover" @mouseover="hover = true" @mouseleave="hover = false" v-if="hover" v-on:click="add()" />
    </div>
  </template>
  
  <script>
  import { PlusCircleIcon } from "@heroicons/vue/24/outline";
  import { PlusCircleIcon as PlusSolid } from "@heroicons/vue/24/solid";
  export default {
    name: "add-cocktail-btn",
    components: {PlusCircleIcon, PlusSolid},
    data() {
      return{
        hover: false
      }
    },
    methods: {
      add() {
        const route = {
          name: 'addCocktail',
        }
  
        this.$router.push(route)
      }
    }
  };
  </script>
  
  
  <style scoped>
  
  #add-button {
    width: 30px;
  }

  .add {
    stroke: #ffaa00;
  }

  .add-hover {
    fill: #ffaa00;
  }

  .btn {
    cursor: pointer;
  }

  </style>
  