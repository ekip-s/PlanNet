import styles from "./Close.module.css";
import {useNavigate} from "react-router";

interface CloseProps {
    name: string;
    page: string
}

const Close = ({name, page}: CloseProps) => {

    const navigate = useNavigate();

    const pageHandler = () => {
        navigate(page);
    }

    return <div onClick={pageHandler} className={styles.close}>{name}</div>
}

export default Close;