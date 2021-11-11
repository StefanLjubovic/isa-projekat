import axios from 'axios';

let server = {};
const baseUrl = 'http://localhost:8080';

server.getAllEntities = async (state) =>{
    const options ={
        method: 'GET',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            Accept: 'application/json',
        },
        url: `${baseUrl}/client/`+state,
    };
    return axios(options)
    .then(response => handleSuccess(response))
    .catch((error) => handleError(error));
}


function handleError(error) {
	console.log('Error');
	return { success: false, data: error.response.data };
}
function handleSuccess(response) {
	return { success: true, data: response.data || response };
}

export default server;