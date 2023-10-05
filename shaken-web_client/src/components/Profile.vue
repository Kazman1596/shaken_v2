<template>
    <div id="profile">
        <div id="profile-header">
            <div id="profile-image">
                <img v-bind:src="user.profilePicture" />
                <button v-show="!editPic" v-on:click="editPic = !editPic">Edit Image</button>
                <div v-show="editPic">
                    <input type="text" id="editPic" placeholder="Image URL..."/>
                    <button>Update</button>
                    <button v-on:click="editPic = !editPic">Cancel</button>
                </div>
            </div>
            <div id="name">
                <h3>{{ `${user.firstName} ${user.lastName}` }}</h3>
                <p>{{ user.username }}</p>
            </div>
        </div>
        <div id="about">
            <h4>Bio</h4>
            <p>{{ user.bio }}</p>
            <button v-show="!editBio" v-on:click="editBio = !editBio">Edit Bio</button>
            <div v-show="editBio">
                <textarea />
                <button>Update</button>
                <button v-on:click="editBio = !editBio">Cancel</button>
            </div>
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
            bio: "",
        },
        invalidCredentials: false,
        username: "",
        editBio: false,
        editPic: false
      };
    },
    created() {
        this.username = this.$route.params.username;
        accountService.getAccountByUsername(this.username).then((response) => {
            console.log(response.data)
            this.user = response.data
            this.hasProfilePicture();
            this.hasBio();
        });
    },
    methods: {
        hasProfilePicture() {
            console.log(this.user.profilePicture)
            if (!this.user.profilePicture) {
                this.user.profilePicture = "https://img.freepik.com/premium-vector/cocktail-line-icon-cocktail-outline-icon_645658-3893.jpg?w=2000"
            }
        },
        hasBio() {
            console.log(this.user.bio)
            if (!this.user.bio) {
                this.user.bio = "No bio yet."
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
    justify-content: space-evenly;
}

#profile-image {
    display: flex;
    flex-direction: column;
}

#profile-image button {
    margin-top: 15px;
}

#profile-image img {
    width: 200px;
    border-radius: 50%;
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

</style>