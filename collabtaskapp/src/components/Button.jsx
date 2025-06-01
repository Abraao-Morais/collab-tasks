import "../styles/components/Button.css";

export default function Button({ children, type, onClick }) {
  return (
    <button type={type} onClick={onClick}>
      {children}
    </button>
  );
}
