import axios from "axios";

const API_BASE = import.meta.env.VITE_API_BASE;
const client = axios.create({
  baseURL: API_BASE,
  headers: {
    "Content-Type": "application/json",
  },
});

export default client;
