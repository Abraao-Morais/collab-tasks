import { PATH, post } from "./apiService";

export async function login(credentials) {
  const response = await post(
    PATH.LOGIN,
    {},
    {
      auth: { username: credentials.username, password: credentials.password },
    }
  );
  return response;
}

export async function register(body) {
  const response = await post(PATH.REGISTER, body);
  return response;
}

export async function logout() {
  return await post(PATH.LOGOUT);
}
