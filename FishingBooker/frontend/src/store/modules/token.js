import axios from "axios";

const state = {
    token : {},
    role : ''
};

const getters = {
    token : (state) => state.token
};

const actions = {
    async fetchToken({commit}, loginRequest){
        const options = {
            method: 'POST',
            url: 'http://localhost:8081/auth/login',
            data: loginRequest,
        };
        const response =await axios(options)
        console.log(response.data)
        console.log(response.data.accessToken)
        console.log(response.data.roles)
        commit('setToken', response.data.accessToken);
        commit('setRole', response.data.roles);
    },
    async logout({commit}){
        commit('removeToken');
    }
};

const mutations = {
    setToken : (state, token) => (state.token = token), 
    setRole : (state, role) => (state.role = role), 
    removeToken : (state) => (state.token = {})
};

export default {
    state,
    getters,
    actions,
    mutations
}