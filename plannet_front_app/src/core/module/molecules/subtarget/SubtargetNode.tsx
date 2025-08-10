import styles from "./SubtargetNode.module.css";
import {TargetModel} from "../../../models/TargetModel.tsx";
import {TargetStatus} from "../target/TargetNode.tsx";
import ExpandingText from "../../atoms/expanding_text/ExpandingText.tsx";
import IconButton from "../../atoms/btns/IconButton.tsx";
import {openProps} from "../../pages/step_by_step/StepByStepDetailPage.tsx";
import {send} from "../../../api/sendHTTP.tsx";
import {useAuth} from "../../../../keycloak/AuthContext.tsx";

interface SubtargetNodeProps {
    subtarget: TargetModel;
    setOpen: (open: openProps) => void;
    refresh: () => void;
}

const SubtargetNode = ({subtarget, setOpen, refresh}: SubtargetNodeProps) => {

    const {getToken} = useAuth();
    const addAction =() => {
        setOpen({
            isOpen: true,
            type: "action",
            parentId: subtarget.id,
            targetName: subtarget.title,
        })
    }

    const completedHandler = () => {
        send({
            url: `/${subtarget.id}?status=COMPLETED`,
            service: "target",
            method: "PATCH",
            token: getToken(),
            dataType: "not"
        })
            .then(refresh)
    }

    const canceledHandler = () => {
        send({
            url: `/${subtarget.id}?status=CANCELED`,
            service: "target",
            method: "PATCH",
            token: getToken(),
            dataType: "not"
        })
            .then(refresh)
    }

    return <div className={styles.subtargetNode}>
        <div className={styles.subtargetTop}>
            <h5>{subtarget.title}</h5>
            {<TargetStatus status={subtarget.status} />}
        </div>
        <ExpandingText text={subtarget.description} displayedLength={50} label={"Описание:"} />
        <div className={styles.stats}></div>
        <div className={styles.btn}>
            <IconButton
                condition={subtarget.status != "COMPLETED" && subtarget.status != "CANCELED"}
                icon={"pi-file-plus"}
                label={"Добавить действие по цели"}
                onClick={addAction}
            />
            <IconButton
                condition={subtarget.status === "IN_PROGRESS"}
                icon={"pi-check"}
                label={"Завершить цель"}
                onClick={completedHandler}
                className={styles.toInProgress}
            />
            <IconButton
                condition={subtarget.status != "COMPLETED"}
                icon={"pi-times"}
                label={"Отменить цель"}
                onClick={canceledHandler}
                className={styles.toCanceled}
            />
        </div>
    </div>
}

export default SubtargetNode;