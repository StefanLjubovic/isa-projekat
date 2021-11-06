import { createRouter, createWebHistory } from "vue-router";
import MainPage from "../views/MainPage.vue";
import Adventures from "../views/Adventures.vue"
import AdventureDetails from "../views/AdventureDetails.vue"

const routes = [
  { path: "/", name: "MainPage", component: MainPage },
  { path: "/adventures", name: "Adventures", component: Adventures },
  { path: "/adventureDetails/:id", name: "AdventureDetails", component: AdventureDetails }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
