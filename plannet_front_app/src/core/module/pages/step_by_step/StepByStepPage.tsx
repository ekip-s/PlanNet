import style from "./StepByStepPage.module.css";
import Button from "../../atoms/btns/Button.tsx";
import {useNavigate} from "react-router";

const StepByStepPage = () => {

    const navigate = useNavigate();

    const newTargetHandler = () => {
        navigate("/stepByStep/new");
    }

    return <section className={style.stepByStepPage}>
        <div className={style.btnWrapper}>
            <div className={style.info}>
                <h3>Step By Step</h3>
                <p>Шаг за шагом двигайся к цели</p>
            </div>
            <div>
                <Button type={"button"} text={"Новая цель"} onClick={newTargetHandler}/>
            </div>
        </div>
    </section>
}

export default StepByStepPage;