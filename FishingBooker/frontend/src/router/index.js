import { createRouter, createWebHistory } from "vue-router";
import MainPage from "../views/MainPage.vue";
import Adventures from "../views/Adventures.vue"
import ClientRegistration from "../views/ClientRegistration.vue"
import AdventureDetails from "../views/AdventureDetails.vue"
import CottageDetails from "../views/CottageDetails.vue"

const routes = [
  { path: "/", name: "MainPage", component: MainPage },
  { path: "/adventures", name: "Adventures", component: Adventures },
  { path: "/adventureDetails/:id", name: "AdventureDetails", component: AdventureDetails },
  { path: "/cottageDetails/:id",   name: "CottageDetails",   component: CottageDetails },
  { path: "/client-registration", name: "ClientRegistration", component: ClientRegistration }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
