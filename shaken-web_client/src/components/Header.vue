<script>
export default {
  name: "header"
};
</script>

<template>
  <div class="header">
    <div id="logo">
      <h1>Shaken</h1>
    </div>
    <nav>
        <router-link v-bind:to="{name: 'login'}" v-if="$store.state.token == ''">Login</router-link>
        <div v-if="$store.state.token !=''">
            <p>Hello, <b>{{ $store.state.user.firstName }}</b></p>
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
  transform: translateX(-50%)
}

nav {
  flex: 0 1 auto;
  margin-left: auto;
}

</style>
