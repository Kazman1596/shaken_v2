<script>
import { RouterView } from 'vue-router';
import Menu from './components/Menu.vue'
import MainSearch from './components/MainSearch.vue';
export default {
  components: {
    Menu,
    MainSearch
}
}


</script>

<template>
  <Menu />
  <MainSearch />
  
  <RouterView />
</template>

<style scoped>
  header {
    line-height: 1.5;
    max-height: 100vh;
  }


</style>
