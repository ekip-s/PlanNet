import styles from "./Button.module.css"

interface ButtonProps {
    type: 'button' | 'submit';
    text: string;
    className?: string;
    onClick?: () => void;
}

const Button = ({ type, text, className, onClick }:ButtonProps) => {
    const handleClick = type === "button" ? onClick : () => {};

    return (
        <button
            className={`${styles.button} ${[className]}`}
            onClick={handleClick}
            type={type}
        >
            {text}
        </button>
    );
};

export default Button;