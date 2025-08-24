import styles from "./PowerSettings.module.css";
import { InputText } from 'primereact/inputtext';
import {useState} from "react";

const PowerSettings = () => {
    const [aiTunnelToken, setAiTunnelToken] = useState<string>();

    return <div className={styles.powerSettings}>
        <form>
            <h4>Основное:</h4>
            <div>
                <div>
                    <label htmlFor="aiTunnelToken">Токен:</label>
                    <InputText
                        className={styles.inputToken}
                        type={"password"}
                        value={aiTunnelToken}
                        id={'aiTunnelToken'}
                        placeholder={"sk-aitunnel-..."}
                        onChange={(e) => setAiTunnelToken(e.target.value)}
                    />
                </div>
                <div>модель</div>
            </div>
            // настройки тренировок
            // настройки питания
        </form>
    </div>
}

export default PowerSettings;