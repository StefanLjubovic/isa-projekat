import {createStore} from 'vuex'
import token from './modules/token';
import createPersistedState from 'vuex-persistedstate'
export default createStore({
    plugins: [createPersistedState({
        storage: window.sessionStorage,
    })],
    modules: {
        token
    }
});