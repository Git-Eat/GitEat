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
  if (token) {
    config.headers["authorization"] = `${token}`;
  }
  return config;
});

authClient.interceptors.response.use(
  (response) => {
    console.log(response.headers);
    return response;
  },
  (error) => {
    if (error.response.status === 401) {
      localStorage.removeItem("token");
      window.location.replace("/login");
    }
    return Promise.reject(error);
  }
);

export default authClient;
