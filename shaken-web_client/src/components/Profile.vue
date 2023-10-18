<template>
    <div id="profile">
        <div id="profile-header">
            <div id="profile-image">
                <img v-bind:src="user.profilePicture" />
                <button v-show="!editPic && this.$store.state.user.username === this.user.username" v-on:click="editPic = !editPic">Edit Image</button>
                <div class="edit" v-show="editPic">
                    <input v-model="newProfilePicture" type="text" id="editPic" placeholder="Image URL..."/>
                    <div id="img-buttons">
                        <button v-on:click="updateProfile()">Update</button>
                        <button v-on:click="editPic = !editPic">Cancel</button>
                    </div>
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
            <button v-show="!editBio && this.$store.state.user.username === this.user.username" v-on:click="editBio = !editBio">Edit Bio</button>
            <div class="edit" v-show="editBio">
                <textarea v-model="newBio" />
                <div id="bio-buttons">
                    <button v-on:click="updateProfile()">Update</button>
                    <button v-on:click="editBio = !editBio">Cancel</button>
                </div>
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
        newProfilePicture: "",
        newBio: "",
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
        });
    },
    methods: {
        updateProfile() {
            const updatedAccount = {
                id: this.user.id,
                firstName: this.user.firstName,
                lastName: this.user.lastName,
                email: this.user.email,
                profilePicture: (this.newProfilePicture ? this.newProfilePicture : this.user.profilePicture),
                bio: (this.newBio ? this.newBio : this.user.bio),
                dateAdded: this.user.dateAdded,
                username: this.user.username
            }

            accountService.updateAccount(updatedAccount).then(() => {
                this.user = updatedAccount;
                this.$store.commit("SET_USER", updatedUser)
                this.editBio = false;
                this.editPic = false;
            }).catch((error) => {
                if(error.response) {
                    alert("Something went wrong: " + error.response.statusTest)
                } else {
                    // This error is showing and its incredibly unhelpful. We will come back to this.
                    alert("Unknown error occured")
                }
            })
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

#name {
    margin-left: 150px;
}

.edit {
    display: flex;
    flex-direction: column;
    margin-top: 15px;
}

#bio-buttons {
    margin-top: 5px;
    margin-left: 380px;
}

#bio-buttons button {
    margin: 5px;
}

#img-buttons {
    margin-top: 5px;
    margin-left: 135px;
}

#img-buttons button {
    margin: 5px;
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

input{
    border: solid 1px #00eeff;
    border-radius: 10px;
    padding: 10px;
    width: 250px;
}

textarea {
    border: solid 1px #00eeff;
    border-radius: 10px;
    padding: 10px;
    width: 500px;
    height: 100px;
}

</style>