import styles from "./IconButton.module.css";

interface IconButtonProps {
    condition: boolean | undefined;
    icon: string;
    label: string;
    onClick: () => void;
    className?: string;
}

const IconButton = ({condition, icon, label, onClick, className}:IconButtonProps) => {

    if(condition != undefined && !condition) return;

    return <button className={`${className} ${styles.iconButton}`} onClick={onClick}>
        <i className={`pi ${icon}`}></i>
        <div className={styles.tooltipText}>{label}</div>
    </button>
}

export default IconButton;

