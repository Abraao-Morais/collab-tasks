import { useState } from "react";
import { useNavigate } from "react-router";
import { FaLock, FaUser } from "react-icons/fa";
import { login } from "../services/AuthService";
import Input from "../components/Input";
import Title from "../components/Title";
import Button from "../components/Button";
import "../styles/pages/Login.css";

export default function Login() {
  const [input, setInput] = useState({ username: "", password: "" });
  const [error, setError] = useState();
  const navigate = useNavigate();

  function handleChange(event) {
    const { name, value } = event.target;
    setInput((oldLogin) => ({ ...oldLogin, [name]: value }));
  }

  async function handleSubmit(event) {
    event.preventDefault();

    try {
      console.log(input);
      const token = await login(input);
      console.log(token);
      localStorage.setItem("token", token);
      navigate("/home");
    } catch (error) {
      console.log(error.message);
      if (error.response && error.response.status === 401) {
        setError("Usuário ou senha inválidos.");
      } else {
        setError("Erro de conexão com o servidor.");
      }
    }
  }

  return (
    <main>
      <section className="wrapper">
        <form autoComplete="off" onSubmit={handleSubmit}>
          <Title />
          <div className="input-box">
            <Input
              type="text"
              placeholder="Username"
              name="username"
              value={input.username}
              onChange={handleChange}
            />
            <FaUser className="icon" />
          </div>

          <div className="input-box">
            <Input
              type="password"
              placeholder="Password"
              name="password"
              value={input.password}
              onChange={handleChange}
            />
            <FaLock className="icon" />
          </div>

          <div className="remember">
            <input type="checkbox" placeholder="Remember me" />
          </div>

          <Button className="button" type="submit">
            Login
          </Button>

          {error && <span className="error">{error}</span>}
        </form>
      </section>
    </main>
  );
}
