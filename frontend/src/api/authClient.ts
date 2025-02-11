import axios from "axios";

const API_BASE = import.meta.env.VITE_API_BASE;
const authClient = axios.create({
  baseURL: API_BASE,
  headers: {
    "Content-Type": "application/json",
  },
});

authClient.interceptors.request.use((config) => {
  const token = localStorage.getItem("access_token");
  config.withCredentials = true;
  if (token) {
    config.headers["authorization"] = `${token}`;
  }
  return config;
});

authClient.interceptors.response.use(
  (response) => {
    console.log(response.headers["authorization"]);
    if (response.headers["authorization"]) {
      localStorage.setItem("access_token", response.headers["authorization"]);
    }
    return response;
  },
  (error) => {
    if (error.response.status === 401) {
      localStorage.removeItem("access_token");
      alert("기간만료!");
      window.location.replace("/");
    }
    return Promise.reject(error);
  }
);

export default authClient;
