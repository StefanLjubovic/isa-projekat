import { createRouter, createWebHistory } from "vue-router";
import MainPage from "../views/MainPage.vue";
import Adventures from "../views/Adventures.vue"
const routes = [
  {
    path: "/",
    name: "MainPage",
    component: MainPage,
  },
  {
    path: "/adventures",
    name: "Adventures",
    component: Adventures,
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
