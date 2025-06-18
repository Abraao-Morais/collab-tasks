import axios from "axios";

export const API_URL = "http://backend:8080" || "http://localhost:8080";

export const PATH = {
  LOGIN: "/auth/login",
  LOGOUT: "/logout",
  REGISTER: "/register",
};

const api = axios.create({
  baseURL: API_URL,
});

export const get = api.get;
export const post = api.post;
export const put = api.put;
export const deleteRequest = api.delete;
