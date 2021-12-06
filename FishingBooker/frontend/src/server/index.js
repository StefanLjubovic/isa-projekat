import axios from 'axios';

let server = {};
server.baseUrl = 'http://localhost:8082';

server.getAllEntities = async (state) =>{
    const options ={
        method: 'GET',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            Accept: 'application/json',
        },
        url: server.baseUrl+`/user/`+state,

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
    const options ={
        method: 'GET',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            Accept: 'application/json',
        },
        url: server.baseUrl+`/user/getById/`+id,
    };
    return axios(options)
    .then(response => handleSuccess(response))
    .catch((error) => handleError(error));
};

server.getLoggedUser = async () =>{
    const options ={
        method: 'GET',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            Accept: 'application/json',
        },
        url: server.baseUrl+`/user/getLoggedUser/`,
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