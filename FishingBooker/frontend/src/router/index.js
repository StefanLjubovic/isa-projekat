import { createRouter, createWebHistory } from "vue-router";
import MainPage from "../views/MainPage.vue";
import Homepage from "../views/Homepage.vue"
import ClientRegistration from "../views/ClientRegistration.vue"
import AdvertiserRegistation from "../views/AdvertiserRegistration.vue"
import AdventureDetails from "../views/AdventureDetails.vue"
import CottageDetails from "../views/CottageDetails.vue"
import AddNewCottage from "../views/AddNewCottage.vue"
import EditCottage from "../views/EditCottage.vue"
import AddNewAdventure from "../views/AddNewAdventure.vue"

const routes = [
  { path: "/", name: "MainPage", component: MainPage },
  { path: "/homepage", name: "Homepage", component: Homepage },
  { path: "/adventureDetails/:id", name: "AdventureDetails", component: AdventureDetails },
  { path: "/cottageDetails/:id",   name: "CottageDetails",   component: CottageDetails },
  { path: "/client-registration", name: "ClientRegistration", component: ClientRegistration },
  { path: "/advertiser-registration", name: "AdvertiserRegistration", component: AdvertiserRegistation},
  { path: "/addNewCottage", name: "AddNewCottage", component: AddNewCottage},
  { path: "/editCottage/:id", name:"EditCotage", component: EditCottage},
  { path: "/addNewAdventure", name: "AddNewAdventure", component: AddNewAdventure}
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
