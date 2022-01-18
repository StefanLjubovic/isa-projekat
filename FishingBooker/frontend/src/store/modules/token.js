import axios from "axios";

const state = {
    token : '',
    role : '',
    longitude: '',
    latitude: '',
};

const getters = {
    getToken : (state) => state.token,
    getRole : (state) => state.role,
    getLongitude : (state) => state.longitude,
    getLatitude : (state) => state.latitude
};

const actions = {       // takodje za izmenu stanja iz state ali asinhrono, kada je fetchuje neki url
    async fetchToken({commit}, loginRequest){
        const options = {
            method: 'POST',
            url: process.env.VUE_APP_BACKEND_URL+'/auth/login',
            data: loginRequest,
        };
        const response = await axios(options)
        commit('setToken', response.data.accessToken);
        commit('setRole', response.data.roles);
        console.log(response.data.accessToken);
        //console.log('frtch',response.data.roles)
    },
    async logout({commit}){
        const token = state.token
        commit('setToken', {});
        commit('setRole', '');
        const options = {
            method: 'POST',
            url: process.env.VUE_APP_BACKEND_URL+'/auth/logout',
            'Authorization': `Bearer ${token}`
        };
        await axios(options)
    },

    async updateToken({commit}, data){
        console.log(data)
        commit('setToken', data.accessToken);
        commit('setRole', data.roles);
    }
};

const mutations = {     // sinhrono, za izmenu stanja promenljivih u state
    setToken : (state, token) => (state.token = token), 
    setRole : (state, role) => (state.role = role),
    setLongitude : (state, longitude) => (state.longitude = longitude),
    setLatitude : (state, latitude) => (state.latitude = latitude)
};

export default {
    state,
    getters,
    actions,
    mutations
}