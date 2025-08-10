import styles from "./Button.module.css";
import { Button as ButtonPrime } from "primereact/button";

interface ButtonProps {
    type: 'button' | 'submit';
    text: string;
    className?: string;
    onClick?: () => void;
    loading?: boolean;
}

const Button = ({ type, text, className, onClick, loading }:ButtonProps) => {
    const handleClick = type === "button" ? onClick : () => {};

    return <ButtonPrime
        className={className ? className : styles.button}
        label={text}
        type={type}
        onClick={handleClick}
        loading={loading}

    />
};

export default Button;

