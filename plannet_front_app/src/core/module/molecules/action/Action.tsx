import styles from "./Action.module.css";
import {TargetModel} from "../../../models/TargetModel.tsx";
import {formatDate} from "../../../date/DateFormat.ts";

interface ActionProps {
    action: TargetModel;
}

const Action = ({action}: ActionProps) => {
    return <div className={styles.action}>
        <div>{action.title}</div>
        <div>{formatDate(action.createdAt)}</div>
    </div>
}

export default Action;