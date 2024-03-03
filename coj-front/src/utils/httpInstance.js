import axios from 'axios';

const httpInstance = axios.create({
  baseURL: "/api",
  // baseURL: "http://127.0.0.1:11111",
  // baseURL: "http://120.26.15.28:11111",
  timeout: 5000,
});

export default httpInstance;