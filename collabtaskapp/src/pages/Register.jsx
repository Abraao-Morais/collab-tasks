import { useState } from "react";
import { NavLink, useNavigate } from "react-router";
import { register } from "../services/AuthenticationService";
import Input from "../components/Input";
import Title from "../components/Title";
import Button from "../components/Button";
import "../styles/pages/Register.css";

export default function Register() {
  const [input, setInput] = useState({
    name: "",
    email: "",
    nickname: "",
    dateBirth: "",
    password: "",
  });
  const [error, setError] = useState();
  const navigate = useNavigate();

  function handleChange(event) {
    const { name, value } = event.target;
    setInput((oldLogin) => ({ ...oldLogin, [name]: value }));
  }

  async function handleSubmit(event) {
    event.preventDefault();

    try {
      await register(input);
      navigate("/");
    } catch (error) {
      console.log(error.message);
      setError("Erro de conexão com o servidor.");
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
              placeholder="Name"
              name="name"
              value={input.name}
              onChange={handleChange}
            />
          </div>

          <div className="input-box">
            <Input
              type="text"
              placeholder="Email"
              name="email"
              value={input.email}
              onChange={handleChange}
            />
          </div>

          <div className="input-box">
            <Input
              type="text"
              placeholder="Nickname"
              name="nickname"
              value={input.nickname}
              onChange={handleChange}
            />
          </div>

          <div className="input-box">
            <Input
              type="date"
              placeholder="DateBirth"
              name="dateBirth"
              value={input.dateBirth}
              onChange={handleChange}
            />
          </div>

          <div className="input-box">
            <Input
              type="password"
              placeholder="Password"
              name="password"
              value={input.password}
              onChange={handleChange}
            />
          </div>

          <Button type="submit">Register</Button>

          {error && <span className="error">{error}</span>}

          <div className="register">
            <p>
              Have an account? <NavLink to="/login">Log in</NavLink>
            </p>
          </div>
        </form>
      </section>
    </main>
  );
}
