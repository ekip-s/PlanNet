import styles from "./Card.module.css"
import * as React from "react";

interface CardProps {
    children?: React.ReactNode;
    className?: string;
}

const Card = ({ children, className }:CardProps) => {
    return <div className={`${styles.card} ${[className]}`}>{[children]}</div>;
}

export default Card;