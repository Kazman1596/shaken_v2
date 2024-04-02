<template>
  <div id="login">
    <form @submit.prevent="login">
      <h1 >Please Sign In</h1>
      <div role="alert" v-if="invalidCredentials">
        Either the user name or password is invalid.
      </div>
      <div role="alert" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </div>
      <div class="form-input-group">
        <input type="text" id="username" v-model="user.username" placeholder="Username" required autofocus />
      </div>
      <div class="form-input-group">
        <input type="password" id="password" v-model="user.password" placeholder="Password" required />
      </div>
      <button type="submit">Sign in</button>
      <p>
      <router-link :to="{ name: 'register' }">Need an account? Sign up.</router-link></p>
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      const userCred = {
        username: this.user.username.toLowerCase(),
        password: this.user.password
      }
      authService
        .login(userCred)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401 || response.status === 403) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>

<style scoped>

#login {
  text-align: center;
  border: 1px solid #00eeff;
  border-radius: 15px;
  margin: 100px 30%;
}
.form-input-group {
  margin-bottom: 1rem;
}

label {
  margin-right: 0.5rem;
}

button {
  border: solid 1px #ffaa00;
  border-radius: 10px;
  padding: 6px;
  cursor: pointer;
}

button:hover {
  border: solid 1px #00eeff;
  color: #00eeff;
  transition-duration: 250ms;
}

button:not(:hover) {
  transition-duration: 250ms;
}

input {
  padding-right: 20%;
  padding-top: 7px;
  padding-bottom: 7px;
  padding-left: 10px;
  font-size: 14px;
  border: solid 1px #ffffff;
  border-radius: 8px;
  margin: 10px;
}

::placeholder {
  color: #d3d3d3;
}
</style>