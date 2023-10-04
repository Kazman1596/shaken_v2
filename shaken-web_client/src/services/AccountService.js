import axios from 'axios';

export default {

  getAccountByUsername(username) {
    return axios.get(`/accounts/user/${username}`)
  },

  getAccountById(id) {
    return axios.get(`/accounts/${id}`)
  }

}