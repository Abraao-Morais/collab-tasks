import { useState } from "react";
import { NavLink, useNavigate } from "react-router";
import { FaUser, FaLock } from "react-icons/fa";
import { login } from "../../services/authService";
import "./LoginForm.css";

export default function LoginForm() {
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
      await login(input);
      navigate("/main/feed");
    } catch (error) {
      console.log(error.message);
      if (error.response && error.response.status === 401) {
        setError("Usuário ou senha inválidos.");
      } else if (error.response) {
        setError(
          `Erro: ${error.response.status} - ${
            error.response.data.message || "Erro desconhecido."
          }`
        );
      } else {
        setError("Erro de conexão com o servidor.");
      }
    }
  }

  return (
    <section className="wrapper">
      <form autoComplete="off" onSubmit={handleSubmit}>
        <h1>Collab</h1>
        <div className="input-box">
          <input
            required
            type="text"
            placeholder="UserName"
            id="username"
            name="username"
            value={input.username}
            onChange={handleChange}
          />
          <FaUser className="icon" />
        </div>

        <div className="input-box">
          <input
            required
            type="password"
            placeholder="Password"
            id="password"
            name="password"
            value={input.password}
            onChange={handleChange}
          />
          <FaLock className="icon" />
        </div>

        <button type="submit">Login</button>

        {error && <span className="error">{error}</span>}

        <div className="register">
          <p>
            Don't have an account? <NavLink to="/register">Register</NavLink>
          </p>
        </div>
      </form>
    </section>
  );
}
