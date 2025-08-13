import style from "./TaskPage.module.css"
import Button from "../../atoms/btns/Button.tsx";
import {useNavigate} from "react-router";

const TaskPage = () => {
    const navigate = useNavigate();

    const toNewTaskHandler = () => {
        navigate("/tasks/new");
    }

    return <section className={style.taskPage}>
        <div className={style.wrapper}>
            <div>Тут селекторы для поиска</div>
            <div>
                <Button
                    type="button"
                    text="Новая задача"
                    onClick={toNewTaskHandler}
                />
            </div>
        </div>
        <div>лист задач</div>
    </section>
}

export default TaskPage;