<template>
  <div class="header">
    <div id="logo">
      <h1 v-on:click="home()">Shaken</h1>
    </div>
    <nav>
        <router-link v-bind:to="{name: 'login'}" v-if="$store.state.token == ''">Login</router-link>
        <div v-if="$store.state.token !=''">
          <div id="profile-greeting">
            <p>Hello, <b>{{ $store.state.user.firstName }}</b></p>
            <router-link v-bind:to="{name: 'userProfile', params: {username: $store.state.user.username}}"><img v-bind:src="$store.state.user.profilePicture" /></router-link>
          </div>
          <router-link v-bind:to="{name: 'logout'}">Logout</router-link>
        </div>
    </nav>
  </div>
</template>

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

#profile-greeting {
  display: flex;
  align-items: center;
}

#profile-greeting img {
  width: 50px;
  border-radius: 50%;
  margin: 15px;
}

</style>
