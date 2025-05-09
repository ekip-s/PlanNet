import styles from './Header.module.css'
import {useNavigate} from "react-router";

const Header = () => {
    const navigate = useNavigate();

    const logoClickHandler = () => {
        navigate("/home")
    }

    return <header className={styles.header}>
        <div className={styles.wrapper}>
            <div className={styles.logo} onClick={logoClickHandler}>
                <h3>Plan Net</h3>
            </div>
            <div></div>
        </div>
    </header>
}

export default Header