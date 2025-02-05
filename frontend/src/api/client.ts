import axios from "axios";

const client = axios.create({
  baseURL: "http://backendApi:8080",
  headers: {
    "Content-Type": "application/json",
  },
});

export default client;
