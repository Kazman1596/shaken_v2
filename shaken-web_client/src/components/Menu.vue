<template>
  <div id="menu">
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
    <div id="nav-buttons">
      <div>
        <router-link v-bind:to="{name: 'search'}"><p :class="isActiveSearch ? 'button active-tab' : 'button'">Search</p></router-link>
      </div>
      <div v-if="$store.state.token != ''">
        <router-link v-bind:to="{name: 'myCocktails'}"><p :class="isActiveMyCocktails ? 'button active-tab' : 'button'">My Cocktails</p></router-link>
      </div>
      <div v-if="$store.state.token != ''">
        <router-link v-bind:to="{name: 'favorites'}"><p :class="isActiveFavorites ? 'button active-tab' : 'button'">Favorites</p></router-link>
      </div>
      <AddCocktailBtn v-if="$store.state.token != ''" />
    </div>
  </div>
</template>

<script>
import AddCocktailBtn from '../components/AddCocktailBtn.vue'
export default {
  name: "menu",
  components: {AddCocktailBtn},
  data() {
    return{
      hover: false,
      isActiveSearch: true,
      isActiveMyCocktails: false,
      isActiveFavorites: false
    }
  },
  methods: {
    home() {
      const route = {
          name: 'search',
        }
  
        this.$router.push(route)
    },
    setActiveTab() {
      this.isActiveSearch = this.$route.name === 'search' || this.$route.name === 'home';
      this.isActiveMyCocktails = this.$route.name === 'myCocktails';
      this.isActiveFavorites = this.$route.name === 'favorites';
    }
  },
  watch: {
    $route() {
      this.setActiveTab();
    }
  }
};
</script>


<style>

#menu {
  border-bottom: 1px solid #a5a5a5;
  margin: 15px;
}

.header {
  display: flex;
  justify-content: space-between;
  min-height: 150px;
}

#nav-buttons div {
  margin-left: 75px;
}

.button {
  cursor: pointer;
  padding-bottom: 2px;
  border-bottom: 1px solid transparent;
}

.button:hover {
  border-bottom: 1px solid #00eeff;
  transition-duration: 200ms;
}

.button:not(:hover) {
  transition-duration: 200ms;
}

.active-tab {
  border-bottom: 1px solid #00eeff;
}

a{
  text-decoration: none;
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

#nav-buttons {
  display: flex;
  align-items: center;
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
  border: 1px solid transparent;
  padding: 2px;
  border-radius: 50%;
  margin: 15px;
}

#profile-greeting img:hover {
  border: 1px solid #ffaa00;
  transition-duration: 250ms;
  opacity: .5;
}

#profile-greeting img:not(:hover) {
  transition-duration: 250ms;
}

#add-button {
  width: 35px;
}
</style>
