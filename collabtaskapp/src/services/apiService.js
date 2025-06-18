import axios from "axios";

export const PATH = {
  LOGIN: "/auth/login",
  LOGOUT: "/logout",
  REGISTER: "/register",
  PATH: process.env.REACT_APP_API_URL || "http://localhost:8080"
};

const api = axios.create({
  baseURL: PATH.PATH,
});

export const get = api.get;
export const post = api.post;
export const put = api.put;
export const deleteRequest = api.delete;
