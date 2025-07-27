import styles from "./NewTask.module.css"
import { InputText } from 'primereact/inputtext';
import Card from "../../molecules/card/Card.tsx";
import {useState} from "react";
import SelectNode from "../../atoms/input_text/SelectNode.tsx";
import { InputTextarea } from 'primereact/inputtextarea';


const NewTask = () => {

    const [priority, setPriority] = useState(1);

    return <section className={styles.newTask}>
        <h3>Новая задача:</h3>
        <Card className={styles.card}>
            <div className={styles.topData}>
                <div>
                    <label>Название задачи:</label>
                    <InputText
                        className={styles.customInput}
                        max={225}
                        placeholder="Прийти на работу"
                    />
                </div>
                <div>
                    <label>Приоритет:</label>
                    <SelectNode
                        placeholder="Normal"
                        selected={priority}
                        setSelected = {setPriority}
                        options={priorityLvl}
                    />
                </div>
            </div>
            <div>
                <label>Описание задачи:</label>
                <InputTextarea />
            </div>
        </Card>
    </section>
}

const priorityLvl = [
    {
        label: "Normal",
        value: 1,
    },
    {
        label: "Medium",
        value: 2,
    },
    {
        label: "High",
        value: 3,
    }
]

export default NewTask;