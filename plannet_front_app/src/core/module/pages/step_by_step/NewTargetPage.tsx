import styles from "./NewTargetPage.module.css";
import Card from "../../molecules/card/Card.tsx";
import {InputText} from "primereact/inputtext";
import {InputTextarea} from "primereact/inputtextarea";
import Button from "../../atoms/btns/Button.tsx";
import {useNavigate} from "react-router";
import React from "react";

const NewTargetPage = () => {

    const navigate = useNavigate();

    const exitFormHandler = () => {
        navigate("/stepByStep")
    }

    const addNewTarget = (event:  React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
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
                        />
                    </div>
                    <div className={styles.top}>
                        <label>Описание задачи:</label>
                        <InputTextarea
                            className={styles.textarea}
                            placeholder="Пройти обучение и устоится на работу ..."
                        />
                    </div>
                    <div className={styles.btn}>
                        <Button type={"submit"} text={"Создать"} />
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