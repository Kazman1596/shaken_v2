import { createApp } from 'vue'
import { store } from './store'
import App from './App.vue'
import router from './router'
import axios from 'axios'

axios.defaults.baseURL = import.meta.env.VITE_APP_REMOTE_API

const app = createApp(App)

app.use(router)
app.use(store)

app.mount('#app')
