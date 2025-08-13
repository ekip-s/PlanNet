import {GroupServiceModel} from "../../../models/GroupModel.tsx";
import styles from "./ServiceNode.module.css"
import {useAuth} from "../../../../keycloak/AuthContext.tsx";
import {deleteServiceByGroup} from "../../../../data/ServiceConnect.ts";


interface ServiceNodeProps {
    service: GroupServiceModel,
    editMode: boolean,
    groupId: string,
    refresh: () => void,
}

const ServiceNode = ({service, editMode, groupId, refresh }:ServiceNodeProps) => {

    const { getToken } = useAuth();

    const deleteServiceByGroupHandler = () => {
        deleteServiceByGroup(
            {
                groupId: groupId,
                serviceName: service.name,
                token: getToken(),
                refresh
            })
    }

    return <div className={styles.serviceNode}>
        <p>{service.name}</p>
        <div>{editMode && <i onClick={deleteServiceByGroupHandler} className="pi pi-times"></i>}</div>
    </div>
}

export default ServiceNode;