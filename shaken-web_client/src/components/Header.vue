<script>
export default {
  name: "header",
  methods: {
    home() {
      const route = {
          name: 'home',
        }
  
        this.$router.push(route)
    }
  }
};
</script>

<template>
  <div class="header">
    <div id="logo">
      <h1 v-on:click="home()">Shaken</h1>
    </div>
    <nav>
        <router-link v-bind:to="{name: 'login'}" v-if="$store.state.token == ''">Login</router-link>
        <div v-if="$store.state.token !=''">
          <div>
            <p>Hello, <b>{{ $store.state.user.firstName }}</b></p>
            <img v-bind:src="$store.state.user.profileImage" />
          </div>
            <router-link v-bind:to="{name: 'logout'}">Logout</router-link>
        </div>
    </nav>
  </div>
</template>

<style>
.header {
  display: flex;
  justify-content: space-between;
}

#logo {
  flex: 0 1 auto;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
}

#logo h1 {
  font-family: 'Oooh Baby', 'Montserrat', cursive;
  letter-spacing: 4px;
  font-size: 42px;
}

#logo h1:hover {
  cursor: pointer;
  color: #ffaa00;
  transition-duration: 250ms;
}

#logo h1:not(:hover) {
  transition-duration: 250ms;
}

nav {
  flex: 0 1 auto;
  margin-left: auto;
}

</style>
