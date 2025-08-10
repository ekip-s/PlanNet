import styles from "./ExpandingText.module.css";
import {useState} from "react";

interface ExpandingTextProps {
    text: string | null;
    label: string;
    displayedLength: number;
}

const ExpandingText = ({text, displayedLength, label} : ExpandingTextProps) => {
    const isLong = text ? text.length >= displayedLength : false;
    const [expanded, setExpanded] = useState(false);
    const displayText = expanded || !isLong
        ? text
        : text ? text.slice(0, displayedLength) : "";

    if (!text) return;

    return <div className={styles.description}>
        <label>{label}</label>
        <div>
        {displayText}
        {isLong && !expanded && (
            <span
                onClick={() => setExpanded(true)}
                aria-label="Показать полный текст"
            >
                        ...
                    </span>
        )}
        </div>
    </div>
}

export default ExpandingText;