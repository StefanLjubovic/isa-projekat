import axios from 'axios';
import localStorage from '../store';
let server = {};
server.baseUrl = process.env.VUE_APP_BACKEND_URL;

server.getAllEntities = async (state) =>{
    const options ={
        method: 'GET',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            Accept: 'application/json',
        },
        url: server.baseUrl+`/entity/`+state,

    };
    return axios(options)
    .then(response => handleSuccess(response))
    .catch((error) => handleError(error));
};

server.registerClient = async (client) =>{
    const options ={
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            Accept: 'application/json',
        },
        data: client,
        url: server.baseUrl+`/auth/signup`,
    };
    return axios(options)
    .then(response => handleSuccess(response))
    .catch((error) => handleError(error));
};

server.getUserById = async (id) =>{
    const token = localStorage.getters.getToken
    const options ={
        method: 'GET',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            Accept: 'application/json',
            'Authorization': `Bearer ${token}`
            
        },
        url: server.baseUrl+`/user/getById/`+id,
    };
    return axios(options)
    .then(response => handleSuccess(response))
    .catch((error) => handleError(error));
};

server.getAvailableEntitiesInPeriod = async (state,content) =>{
    const token = localStorage.getters.getToken
    const options ={
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            Accept: 'application/json',
            'Authorization': `Bearer ${token}`
            
        },
        url: server.baseUrl+`/entity/get-available-for-period/`+state,
        data: content,
    };
    return axios(options)
    .then(response => handleSuccess(response))
    .catch((error) => handleError(error));
};

server.getLoggedUser = async () =>{
    const token = localStorage.getters.getToken
    console.log(localStorage.getters.getToken)
    const options ={
        method: 'GET',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            Accept: 'application/json',
            'Authorization': `Bearer ${token}`
        },
        url: server.baseUrl+`/user/getLoggedUser/`,
    }; 
    return axios(options)
    .then(response => handleSuccess(response))
    .catch((error) => handleError(error));
};

server.getSubscriptions= async (token) =>{
    const options ={
        method: 'GET',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            Accept: 'application/json',
            'Authorization': `Bearer ${token}`
        },
        url: server.baseUrl+`/entity/subscriptions/`,
    }; 
    return axios(options)
    .then(response => handleSuccess(response))
    .catch((error) => handleError(error));
};

server.saveDeleteRequest= async (content,token) =>{
    const options ={
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            Accept: 'application/json',
            'Authorization': `Bearer ${token}`
        },
        url: server.baseUrl+`/deleteRequest`,
        data : content
    }; 
    return axios(options)
    .then(response => handleSuccess(response))
    .catch((error) => handleError(error));
};

server.saveReservation= async (content) =>{
    const token = localStorage.getters.getToken
    const options ={
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            Accept: 'application/json',
            'Authorization': `Bearer ${token}`
        },
        url: server.baseUrl+`/reservation/`,
        data : content
    }; 
    return axios(options)
    .then(response => handleSuccess(response))
    .catch((error) => handleError(error));
};

server.saveFastReservation= async (content) =>{
    const token = localStorage.getters.getToken
    const options ={
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            Accept: 'application/json',
            'Authorization': `Bearer ${token}`
        },
        url: server.baseUrl+`/reservation/fast-reservation/`,
        data : content
    }; 
    return axios(options)
    .then(response => handleSuccess(response))
    .catch((error) => handleError(error));
};

server.getFutureReservations= async () =>{
    const token = localStorage.getters.getToken
    const options ={
        method: 'GET',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            Accept: 'application/json',
            'Authorization': `Bearer ${token}`
        },
        url: server.baseUrl+`/reservation/future-reservations`,
    }; 
    return axios(options)
    .then(response => handleSuccess(response))
    .catch((error) => handleError(error));
};

server.cancelReservation= async (id) =>{
    const token = localStorage.getters.getToken
    const options ={
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            Accept: 'application/json',
            'Authorization': `Bearer ${token}`
        },
        url: server.baseUrl+`/reservation/cancel-reservation/`+id,
    }; 
    return axios(options)
    .then(response => handleSuccess(response))
    .catch((error) => handleError(error));
};

server.getHistoryOfReservations= async (classType) =>{
    const token = localStorage.getters.getToken
    const options ={
        method: 'GET',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            Accept: 'application/json',
            'Authorization': `Bearer ${token}`
        },
        url: server.baseUrl+`/reservation/history-reservations/`+classType,
    }; 
    return axios(options)
    .then(response => handleSuccess(response))
    .catch((error) => handleError(error));
};

server.saveClientRevision= async (content) =>{
    const token = localStorage.getters.getToken
    const options ={
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            Accept: 'application/json',
            'Authorization': `Bearer ${token}`
        },
        url: server.baseUrl+`/revision/save-revision/`,
        data : content
    }; 
    return axios(options)
    .then(response => handleSuccess(response))
    .catch((error) => handleError(error));
};

server.saveComplaint= async (content) =>{
    const token = localStorage.getters.getToken
    const options ={
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            Accept: 'application/json',
            'Authorization': `Bearer ${token}`
        },
        url: server.baseUrl+`/complaint/save-complaint/`,
        data : content
    }; 
    return axios(options)
    .then(response => handleSuccess(response))
    .catch((error) => handleError(error));
};

server.checkIfSubscibed= async (id) =>{
    const token = localStorage.getters.getToken
    const options ={
        method: 'GET',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            Accept: 'application/json',
            'Authorization': `Bearer ${token}`
        },
        url: server.baseUrl+`/entity/check-subscription/`+id,
    }; 
    return axios(options)
    .then(response => handleSuccess(response))
    .catch((error) => handleError(error));
};

server.updateUser = async(data,token)=>{
    const options ={
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            Accept: 'application/json',
            'Authorization': `Bearer ${token}`
        },
        url: server.baseUrl+`/user/update/`,
        data : data
    }; 
    return axios(options)
    .then(response => handleSuccess(response))
    .catch((error) => handleError(error));
};

server.alterSubscriptions = async(id)=>{
    const token = localStorage.getters.getToken
    const options ={
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            Accept: 'application/json',
            'Authorization': `Bearer ${token}`
        },
        url: server.baseUrl+`/user/alter-subscriptions/`+id,
    }; 
    return axios(options)
    .then(response => handleSuccess(response))
    .catch((error) => handleError(error));
};

server.getEntitiesOnSale = async(state)=>{
    console.log('tuuuu')
    const token = localStorage.getters.getToken
    const options ={
        method: 'GET',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            Accept: 'application/json',
            'Authorization': `Bearer ${token}`
        },
        url: server.baseUrl+`/entity/get-on-sale/`+state,
    }; 
    return axios(options)
    .then(response => handleSuccess(response))
    .catch((error) => handleError(error));
};


server.changePassword = async(password,token)=>{
    const options ={
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            Accept: 'application/json',
            'Authorization': `Bearer ${token}`
        },
        url: server.baseUrl+`/user/changePassword/` + password,
    }; 
    return axios(options)
    .then(response => handleSuccess(response))
    .catch((error) => handleError(error));
};


function handleError(error) {
	console.log('Error');
	return { success: false, data: error.response.data };
}
function handleSuccess(response) {
	return { success: true, data: response.data || response };
}

export default server;