import styles from "./NewTargetPage.module.css";
import Card from "../../molecules/card/Card.tsx";
import {InputText} from "primereact/inputtext";
import {InputTextarea} from "primereact/inputtextarea";
import Button from "../../atoms/btns/Button.tsx";
import {useNavigate} from "react-router";
import React, {useRef, useState} from "react";
import Error from "../../molecules/error/Error.tsx";
import {send} from "../../../api/sendHTTP.tsx";
import {useAuth} from "../../../../keycloak/AuthContext.tsx";

const NewTargetPage = () => {
    const navigate = useNavigate();
    const titleRef = useRef<HTMLInputElement>(null);
    const descriptionRef = useRef<HTMLTextAreaElement>(null);
    const {getToken} = useAuth();
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const exitFormHandler = () => {
        navigate("/stepByStep")
    }

    const addNewTarget = (event:  React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        const body = {
            type: "target",
            title: titleRef.current?.value,
            description: descriptionRef.current?.value,
        };
        send({
            url: "",
            service: "target",
            method: "POST",
            body: body,
            token: getToken(),
            dataType: "not",
            setLoading,
            setError
        }).then(() => {
            navigate(`/stepByStep`);
        });
    }

    return <section className={styles.newTargetPage}>
        <div className={styles.wrapper}>
            <Card className={styles.card}>
                <form onSubmit={addNewTarget}>
                    <h3>Новая цель</h3>
                    <div>
                        <label>Название:</label>
                        <InputText
                            className={styles.customInput}
                            max={225}
                            placeholder="Стать космонавтом"
                            ref={titleRef}
                            required={true}
                        />
                    </div>
                    <div className={styles.top}>
                        <label>Описание задачи:</label>
                        <InputTextarea
                            className={styles.textarea}
                            placeholder="Пройти обучение и устоится на работу ..."
                            ref={descriptionRef}
                        />
                    </div>
                    {error && <div className={styles.error}>
                        <Error message={"Ошибка. Попробуйте позже."} />
                    </div>}
                    <div className={styles.btn}>
                        <Button type={"submit"} text={"Создать"} loading={loading} />
                        <Button
                            type={"button"}
                            text={"Закрыть"}
                            className={styles.exit}
                            onClick={exitFormHandler}
                        />
                    </div>
                </form>
            </Card>
        </div>
    </section>
}

export default NewTargetPage;