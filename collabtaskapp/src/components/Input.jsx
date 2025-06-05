import "../styles/components/Input.css";

export default function Input({ type, placeholder, name, value, onChange }) {
  return (
    <input
      required
      type={type}
      placeholder={placeholder}
      id={name}
      name={name}
      value={value || ""}
      onChange={onChange}
    />
  );
}
