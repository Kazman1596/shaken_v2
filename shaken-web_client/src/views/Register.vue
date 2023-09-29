<template>
  <div id="register" class="text-center">
    <form @submit.prevent="register">
      <h1>Create Account</h1>
      <div role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <div class="form-input-group">
        <input type="text" id="firstName" v-model="user.firstName" placeholder="First Name" required autofocus />
      </div>
      <div class="form-input-group">
        <input type="text" id="lastName" v-model="user.lastName" placeholder="Last Name" required autofocus />
      </div>
      <div class="form-input-group">
        <input type="email" id="email" v-model="user.email" placeholder="Email" required autofocus />
      </div>
      <div class="form-input-group">
        <input type="text" id="username" v-model="user.username" placeholder="Username" required autofocus />
      </div>
      <div class="form-input-group">
        <input type="password" id="password" v-model="user.password" placeholder="Password" required />
      </div>
      <div class="form-input-group">
        <input type="password" id="confirmPassword" v-model="user.confirmPassword" placeholder="Confirm Password" required />
      </div>
      <button type="submit">Create Account</button>
      <p><router-link :to="{ name: 'login' }">Already have an account? Log in.</router-link></p>
    </form>
  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'register',
  data() {
    return {
      user: {
        firstName: '',
        lastName: '',
        email: '',
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style scoped>
#register {
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
