import styles from "./StepByStepDetailPage.module.css";
import {useParams} from "react-router";
import Card from "../../molecules/card/Card.tsx";
import useApi from "../../../api/useApi.tsx";
import Loading from "../../molecules/loading/Loading.tsx";
import Error from "../../molecules/error/Error.tsx";
import {TargetDetailModel} from "../../../models/TargetModel.tsx";
import {TargetStatus} from "../../molecules/target/TargetNode.tsx";
import ExpandingText from "../../atoms/expanding_text/ExpandingText.tsx";
import SideForm from "../../organisms/side_form/SideForm.tsx";
import {useState} from "react";
import SubtargetNode from "../../molecules/subtarget/SubtargetNode.tsx";

export interface openProps {
    isOpen: boolean;
    type: "subtarget" | "action"
    parentId: string;
    targetName: string;
}

const StepByStepDetailPage = () => {
    const { id } = useParams();
    const [open, setOpen] = useState<openProps>({
        isOpen: false,
        type: "subtarget",
        parentId: "",
        targetName: "",
    });
    const {data, loading, error, refresh} = useApi<TargetDetailModel>({
        url: `/${id}`,
        service: "target"
    });

    if (loading) {
        return <Loading />;
    }

    if (error || Array.isArray(data)) {
        return <Error message={"Ошибка загрузки данных"} />;
    }

    return <Card className={styles.stepByStepDetailPage}>
        <div className={styles.wrapper}>
            <div>
                <h4>{data.title}</h4>
                <ExpandingText text={data.description} label={"Описание:"} displayedLength={50} />
            </div>
            <TargetStatus status={data.status} />
        </div>
        <div className={styles.subtargets}>
            {data.subtarget.map((item, index) => (
                <SubtargetNode
                    key={`subtargets_node_${index}`}
                    subtarget={item}
                    setOpen={setOpen}
                    refresh={refresh}
                />
            ))}
        </div>
        <SideForm
            open={open}
            setOpen={setOpen}
            targetParentId={data.id}
            refresh={refresh}
        />
    </Card>
}

export default StepByStepDetailPage;