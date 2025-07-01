import {useNavigate, useParams} from "react-router";
import styles from "./JoinGroup.module.css";
import Button from "../../atoms/btns/Button.tsx";
import {joinToGroup} from "../../../../data/GroupUserConnect.ts";
import {useAuth} from "../../../../keycloak/AuthContext.tsx";
import {useState} from "react";
import Error from "../../molecules/error/Error.tsx";

const JoinGroup = () => {
    const { code } = useParams();
    const navigate = useNavigate();
    const {getToken} = useAuth();
    const [error, setError] = useState<string>();

    const toGroupNav = () => {
        if(!error) {
            navigate("/profile/group");
        }
    }

    const joinToGroupHandler = () => {
        joinToGroup({
            code,
            token: getToken(),
            toGroupNav,
            setError
        })
    }

    if (error) {
        return <Error message="Ошибка при присоединении к группе. Возможно приглашение удалено или истекло."/>
    }

    return <section className={styles.joinGroup}>
        <div className={styles.content}>
            <h3>Вступить в группу:</h3>
            <p>У тебя приглашение для вступления в группу. Присоединяемся к группе?</p>
            <div className={styles.btn}>
                <Button
                    type='button'
                    text="Присоединится к группе"
                    onClick={joinToGroupHandler}
                />
            </div>
        </div>
    </section>
}

export default JoinGroup;