import styles from "./GroupManager.module.css";
import Button from "../atoms/btns/Button.tsx";
import useApi from "../../api/useApi.tsx";
import {GroupModel} from "../../models/GroupModel.tsx";
import Loading from "../molecules/loading/Loading.tsx";
import Error from "../molecules/error/Error.tsx";
import GroupNode from "../atoms/GroupNode.tsx";
import {useAuth} from "../../../keycloak/AuthContext.tsx";
import {addNewGroup} from "../../../data/GroupConnect.ts";


const GroupManager = () => {

    const {data, loading, error, refresh} = useApi<GroupModel[]>({
        service: "group",
    })
    const { getUserId, getToken } = useAuth();

    if (error) return <Error message={"Ошибка загрузки групп, попробуйте позже"}/>;

    if (loading || data === null) return <Loading />;

    const addNewGroupHandler = () => {
        addNewGroup({token: getToken(), refresh: refresh });
    }

    return <section className={styles.groupManager}>
        <div className={styles.wrapper}>
            <h4>Мои группы</h4>
            <Button onClick={addNewGroupHandler} type="button" text="Создать группу"/>
        </div>
        <div>
            {
                data.map((group: GroupModel) => {
                    return <GroupNode
                        key={`group_node_${group.id}`}
                        group={group}
                        userId={getUserId()}
                        refresh={refresh}
                    />
                })
            }
        </div>
    </section>
}

export default GroupManager;