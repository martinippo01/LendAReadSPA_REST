import axios, {AxiosInstance} from 'axios';
import Qs from 'qs';
import {jwtDecode} from "jwt-decode";
import {logoutStorages} from "../../contexts/authContext.tsx";

let baseUrl = `${import.meta.env.VITE_APP_BASE_URL}${import.meta.env.VITE_APP_BASE_PATH}`

if(import.meta.env.VITE_APP_BASE_URL === undefined && import.meta.env.VITE_APP_BASE_PATH === undefined) {
    baseUrl = 'http://localhost:8080/';
}

const api = axios.create({
    baseURL: baseUrl + "api",
    timeout: 5000,
    paramsSerializer: params => Qs.stringify(params, { arrayFormat: 'repeat' })
});

const api_ = axios.create({
    timeout: 5000,
    paramsSerializer: params => Qs.stringify(params, { arrayFormat: 'repeat' })
});

api.interceptors.response.use(response => {
    return response;
}, async (error) => {await handleError(error, api)});

api_.interceptors.response.use(response => {
    return response;
}, async (error) => {await handleError(error, api_)});

const refreshToken = async () => {
    const rememberMe = localStorage.getItem("rememberMe");
    const refreshToken = rememberMe === "true" ? localStorage.getItem("refreshToken") : sessionStorage.getItem('refreshToken');

    if (refreshToken === "") {
        console.log("No refresh token found");
        return null;
    }

    try {
        // const decoded = jwtDecode(refreshToken);
        // const currentTime = Date.now() / 1000;
        //
        // if (decoded.exp < currentTime) {
        //     console.log("Refresh token expired");
        //     return null;
        // }

        api.defaults.headers.common['Authorization'] = `Bearer ${refreshToken}`;
        const response = await api.get('/');

        //@ts-ignore
        const token = response.headers.get('x-jwt')

        if(token === null)
            return null

        if (rememberMe === "true") localStorage.setItem('userAuthToken', token);
        else sessionStorage.setItem('userAuthToken', token);

        return token;
    } catch (error) {
        console.error("Error decoding refresh token", error);
        return null;
    }
}

const handleError = async (error, Api: AxiosInstance) => {

    const originalRequest = error.config;

    // Check if the response is a token expired error
    if (error.response !== undefined && error.response.status === 401 && !originalRequest._retry) {
        originalRequest._retry = true;

        const newToken = await refreshToken();

        if(newToken === null) {
            logoutStorages()
            return Promise.reject(error)
        }

        Api.defaults.headers.common['Authorization'] = `Bearer ${newToken}`;
        originalRequest.headers['Authorization'] = `Bearer ${newToken}`;

        try {
            const newRequest = await Api(originalRequest);
            if(newRequest.request.status === 401) {
                logoutStorages()
                return Promise.reject(error)
            }

            return newRequest
        } catch (error) {
            logoutStorages()
            return Promise.reject(error)
        }

    }

    return Promise.reject(error);
}



export { api, api_ };
