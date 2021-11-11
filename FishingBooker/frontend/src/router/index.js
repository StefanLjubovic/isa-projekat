import { createRouter, createWebHistory } from "vue-router";
import MainPage from "../views/MainPage.vue";
import Homepage from "../views/Homepage.vue"
import ClientRegistration from "../views/ClientRegistration.vue"
import AdventureDetails from "../views/AdventureDetails.vue"

const routes = [
  { path: "/", name: "MainPage", component: MainPage },
  { path: "/homepage", name: "Homepage", component: Homepage },
  { path: "/adventureDetails/:id", name: "AdventureDetails", component: AdventureDetails },
  { path: "/client-registration", name: "ClientRegistration", component: ClientRegistration }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
