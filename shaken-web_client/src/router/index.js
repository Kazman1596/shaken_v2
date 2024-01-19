import {createRouter, createWebHistory} from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Logout from '../views/Logout.vue'
import Register from '../views/Register.vue'
import RecipeSearch from '../views/RecipeSearch.vue'
import IngredientSearch from '../views/IngredientSearch.vue'
import Recipe from '../views/Recipe.vue'
import Profile from '../views/Profile.vue'
import AddCocktail from '../views/AddCocktail.vue'
import { store } from '../store'
import MainSearch from '../components/MainSearch.vue'
import MyCocktails from '../components/MyCocktails.vue'
import Favorites from '../components/Favorites.vue'
import EditCocktail from '../views/EditCocktail.vue'


const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        requiresAuth: false
      }
    },
    {
      path:'/search',
      name: 'search',
      component: MainSearch,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/mycocktails',
      name: 'myCocktails',
      component: MyCocktails,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/favorites',
      name: 'favorites',
      component: Favorites,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/logout",
      name: "logout",
      component: Logout,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/search/:input",
      name: "searchRecipe",
      component: RecipeSearch
    },
    {
      path: "/search/ingredients/:ingredients",
      name: "searchIngredients",
      component: IngredientSearch
    },
    {
      path: "/user/:username",
      name: "userProfile",
      component: Profile
    },
    {
      path: "/recipe/:recipeId",
      name: "recipe",
      component: Recipe
    },
    {
      path: "/add",
      name: "addCocktail",
      component: AddCocktail,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/edit/:recipeId",
      name: "editCocktail",
      component: EditCocktail,
      meta: {
        requiresAuth: true
      }
    }
  ]
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    next("/login");
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;
