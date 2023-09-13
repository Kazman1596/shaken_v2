<script>
import { RouterView } from 'vue-router';
import Header from './components/Header.vue'
import MainSearch from './components/MainSearch.vue';
export default {
  components: {
    Header,
    MainSearch
}
}


</script>

<template>
  <Header />
  <MainSearch />

  <RouterView />
</template>

<style scoped>
  header {
    line-height: 1.5;
    max-height: 100vh;
  }


</style>
