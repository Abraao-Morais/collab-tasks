import { PATH, post } from "./AxiosService";

export async function login(credentials) {
  const response = await post("http://localhost:8080/auth/login", {
    username: credentials.username,
    password: credentials.password,
  });
  return response.data;
}

export async function register(body) {
  const response = await post(PATH.REGISTER, body);
  return response;
}

export async function logout() {
  return await post(PATH.LOGOUT);
}
