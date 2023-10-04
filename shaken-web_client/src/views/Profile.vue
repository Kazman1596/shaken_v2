<template>
    <div>
      <div class="profile">
          <Profile></Profile>
      </div>
    </div>
  </template>
  
  <script>
  import Profile from '../components/Profile.vue'
  
  export default {
    name: "user-profile",
    components: {Profile},
    data() {
      return {
        user: {},
        invalidCredentials: false
      };
    },
    created() {
    }
  };
  </script>