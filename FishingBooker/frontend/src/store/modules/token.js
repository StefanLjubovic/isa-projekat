import axios from "axios";

const state = {
    token : {},
    role : ''
};

const getters = {
    getToken : (state) => state.token,
    getRole : (state) => state.role
};

const actions = {       // takodje za izmenu stanja iz state ali asinhrono, kada je fetchuje neki url
    async fetchToken({commit}, loginRequest){
        const options = {
            method: 'POST',
            url: 'http://localhost:8082/auth/login',
            data: loginRequest,
        };
        const response =await axios(options)
        commit('setToken', response.data.accessToken);
        commit('setRole', response.data.roles);
    },
    async logout({commit}){
        commit('setToken', {});
        commit('setRole', '');
    }
};

const mutations = {     // sinhrono, za izmenu stanja promenljivih u state
    setToken : (state, token) => (state.token = token), 
    setRole : (state, role) => (state.role = role)
};

export default {
    state,
    getters,
    actions,
    mutations
}