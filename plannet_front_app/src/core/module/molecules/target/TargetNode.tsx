import styles from "./TargetNode.module.css";
import {TargetModel} from "../../../models/TargetModel.tsx";
import Card from "../card/Card.tsx";
import Button from "../../atoms/btns/Button.tsx";
import {useNavigate} from "react-router";
import ExpandingText from "../../atoms/expanding_text/ExpandingText.tsx";

interface TargetNodeProps {
    target: TargetModel
}

const TargetNode = ({target} : TargetNodeProps) => {

    const navigate = useNavigate();

    const toDetailHandler = () => {
        navigate(`/stepByStep/${target.id}`)
    }

    return <Card className={styles.targetNode}>
        <div className={styles.cardTop}>
            <h4>{target.title}</h4>
            {<TargetStatus status={target.status} />}
        </div>
        <ExpandingText text={target.description} displayedLength={50} label={"Описание:"} />
        <div className={styles.stat}>
            Статистика
        </div>
        <div className={styles.btn}>
            <Button
                type={"button"}
                text={"Подробней ..."}
                className={styles.infoBtn}
                onClick={toDetailHandler}
            />
        </div>
    </Card>
}

interface TargetStatusPops {
    status: string;
}

export const TargetStatus = ({status}: TargetStatusPops) => {
    if (status === "NEW") {
        return <div className={`${styles.status} ${styles.new}`}>Новая</div>;
    } else if (status === "IN_PROGRESS") {
        return <div className={`${styles.status} ${styles.in_progress}`}>В работе</div>;
    } else if (status === "COMPLETED") {
        return <div className={`${styles.status} ${styles.completed}`}>Выполнено</div>;
    } else if (status === "CANCELED") {
        return <div className={`${styles.status} ${styles.canceled}`}>Отменено</div>;
    } else {
        return <div className={`${styles.status} ${styles.new}`}>{status}</div>;
    }
}

export default TargetNode;