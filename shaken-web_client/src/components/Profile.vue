<template>
    <div id="profile">
        <div id="profile-header">
            <div id="profile-image">
                <img src="https://img.freepik.com/premium-vector/cocktail-line-icon-cocktail-outline-icon_645658-3893.jpg?w=2000" />
                <button>Edit Image</button>
            </div>
            <div id="name">
                <h3>{{ `${user.firstName} ${user.lastName}` }}</h3>
                <p>{{ user.username }}</p>
            </div>
        </div>
        <div id="about">
            <h4>Bio</h4>
            <p>{{ user.bio }}</p>
            <button>Edit Bio</button>
        </div>
    </div>
  </template>
  
  <script>
  import accountService from '../services/AccountService';
  
  export default {
    name: "user-details",
    data() {
      return {
        user: {
            profilePicture: "",
        },
        invalidCredentials: false,
        username: ""
      };
    },
    created() {
        this.username = this.$route.params.username;
        accountService.getAccountByUsername(this.username).then((response) => {
            console.log(response.data)
            this.user = response.data
        });
        this.profilePicture();
    },
    methods: {
        // work in progress //
        profilePicture() {
            if (this.user.profilePicture == " ") {
                this.user.profilePicture = "https://img.freepik.com/premium-vector/cocktail-line-icon-cocktail-outline-icon_645658-3893.jpg?w=2000"
            }
        }
    }
  };
</script>

<style>

#profile {
    margin: 100px 10%;
}

#profile-header {
    display: flex;
    align-items: center;
    justify-content: center;
}

#profile-image img {
    width: 200px;
    border-radius: 50%;
}

</style>