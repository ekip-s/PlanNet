import styles from "./SideForm.module.css";
import { InputText } from 'primereact/inputtext';
import { InputTextarea } from 'primereact/inputtextarea';
import Button from "../../atoms/btns/Button.tsx";
import {openProps} from "../../pages/step_by_step/StepByStepDetailPage.tsx";
import React, {useRef} from "react";
import {send} from "../../../api/sendHTTP.tsx";
import {useAuth} from "../../../../keycloak/AuthContext.tsx";

interface SideFormProps {
    open: openProps;
    setOpen: (open: openProps) => void;
    targetParentId: string;
    refresh: () => void;
}

const

    SideForm = ({
                      open,
                      setOpen,
                      targetParentId,
                      refresh
} : SideFormProps) => {

    const {getToken} = useAuth();
    const titleRef = useRef<HTMLInputElement>(null);
    const descriptionRef = useRef<HTMLTextAreaElement>(null);

    const onSubmitHandler = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        send({
            url: "",
            service: "target",
            method: "POST",
            body: {
                parentId: open.parentId,
                type: open.type,
                title: titleRef.current?.value,
                description: descriptionRef.current?.value,
            },
            token: getToken(),
            dataType: "not"
        })
            .then(
                refresh
            )

        setOpen({
            isOpen: false,
            type: "subtarget",
            parentId: "",
            targetName: ""
        })
    }

    return <>
        {!open.isOpen && <button
                className={`${styles.btn} ${open.isOpen && styles.openBtn}`}
                aria-label={open.isOpen ? "Закрыть форму" : "Открыть форму"}
                onClick={() => setOpen({
                    isOpen: true,
                    type: "subtarget",
                    parentId: targetParentId,
                    targetName: ""
                })}
            >
                <i className={"pi pi-file-import"} />
            </button>}

        <div className={`${styles.form} ${open.isOpen && styles.formOpen}`}>
            <div className={styles.formHeader}>
                {open.type === "subtarget" ? <h3>Создать цель</h3> : <h3>Создать действие</h3>}
            </div>
            <hr />
            <div className={styles.info}>
                {open.type === "subtarget" ?
                    <p>Будет создана дочерняя цель к основной</p> :
                    <p>Будет создано действие к цели: "{open.targetName}". Действия влияют на достижение цели.</p>}
            </div>
            <form onSubmit={onSubmitHandler}>
                <div className={styles.title}>
                    <label>Название</label>
                    <InputText
                        className={styles.input}
                        placeholder={open.type === "subtarget" ? "Новая цель": "Новое действие"}
                        required={true}
                        ref={titleRef}
                    />
                </div>
                <div className={styles.description}>
                    <label>Описание:</label>
                    <InputTextarea
                        required={open.type === "subtarget"}
                        className={styles.inputTextarea}
                        placeholder={open.type === "subtarget" ? "Описание цели..." : "Описание действия..."}
                        ref={descriptionRef}
                    />
                </div>
                <div className={styles.formBtn}>
                    <Button type={"submit"} text={"Создать"} className={styles.editBtn}/>
                    <Button type={"button"}
                            text={"Закрыть"}
                            className={styles.exitBtn}
                            onClick={() => setOpen({
                                isOpen: false,
                                type: "subtarget",
                                parentId: "",
                                targetName: ""
                            })}
                    />
                </div>
            </form>
        </div>
    </>
}

export default SideForm;

