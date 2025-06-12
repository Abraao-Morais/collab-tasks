import axios from "axios";

export const PATH = {
  LOGIN: "/auth/login",
  LOGOUT: "/logout",
  REGISTER: "/register",
};

const api = axios.create({
  baseURL: "http://localhost:8080",
});

export const get = api.get;
export const post = api.post;
export const put = api.put;
export const deleteRequest = api.delete;
