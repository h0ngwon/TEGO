import axios from "axios";
import { tokenStore } from "./token";

const host = "https://api.tennisgo.info";
const { getToken } = tokenStore;

const instance = axios.create({
  baseURL: host,
  headers: getToken ? { Authorization: `Bearer ${getToken.jwtToken}` } : null,
});

instance.interceptors.request.use(config => {
  return config;
});

instance.interceptors.response.use(
  response => {
    return response.data;
  },
  // error => {
  //   if (error.response && error.response.status === 500) {
  //     localStorage.removeItem("user");
  //     window.location.reload();
  //   }
  // },
);

export default instance;
