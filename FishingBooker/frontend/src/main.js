import { createApp } from "vue"
import App from "./App.vue"
import router from "./router"
import 'viewerjs/dist/viewer.css'
import VueViewer from 'v-viewer'
import VCalendar from 'v-calendar'
import VueSweetalert2 from 'vue-sweetalert2';

import 'sweetalert2/dist/sweetalert2.min.css';


const app = createApp(App);
app.use(VueSweetalert2);
app.use(VueViewer);
app.use(router);
app.use(VCalendar, {});


app.config.globalProperties.role = 2;
/*  0 - client
    1 - admin
    2 - cottage owner
    3 - ship owner
    4 - fishing instructor
    5 - unregistrated user  -> ovako u enumeraciji za role
*/
app.config.productionTip = false
app.mount("#app");
