import { createRouter, createWebHistory } from "vue-router";
import MainPage from "../views/MainPage";
import Adventures from "../views/Adventures";
import Cotages from "../views/Cotages";
import Ships from "../views/Ships";
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
  },
  {
    path: "/cotages",
    name: "Cotages",
    component: Cotages,
  },
  {
    path: "/ships",
    name: "Ships",
    component: Ships,
  },

  
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
