import styles from "./GroupNode.module.css"
import {GroupModel, GroupServiceModel, GroupUserModel} from "../../models/GroupModel.tsx";
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import Card from "../molecules/card/Card.tsx";
import {useMemo, useState} from "react";
import Button from "./btns/Button.tsx";
import ServiceNode from "./service/ServiceNode.tsx";
import {deleteGroup, setGroupName} from "../../../data/GroupConnect.ts";
import {useAuth} from "../../../keycloak/AuthContext.tsx";
import { InputText } from 'primereact/inputtext';
import SelectService from "./select_service/SelectService.tsx";
import {leaveGroup, removeMember} from "../../../data/GroupUserConnect.ts";
import Invitation from "../molecules/invitation/Invitation.tsx";

interface GroupNodeProps {
    group: GroupModel,
    userId: string | null,
    refresh: () => void,
}

const GroupNode = ({group, userId, refresh}:GroupNodeProps) => {

    const isOwner = useMemo(() => {
        return group.groupUsers?.some(
            user => user.userId === userId && user.role === 'OWNER'
        ) || false;
    }, [group.groupUsers, userId]);
    const [editMode, setEditMode] = useState(false);
    const {getToken} = useAuth();
    const [ name, setName ] = useState<string>(group.name);

    const getBtn = (user: GroupUserModel) => {
        if (editMode && user.userId != userId && isOwner) {
            return <Button className={styles.index} type="button" text="Удалить" onClick={() => removeMemberHandler(user.userId)}/>
        } else if (!editMode && user.userId === userId && !isOwner) {
            return <Button className={styles.index} type="button" text="Выйти" onClick={leaveGroupHandler}/>
        }
        return null;
    }

    const removeMemberHandler = (userId:string) => {
        removeMember({
            groupId: group.id,
            userId: userId,
            token: getToken(),
            refresh
        })
    }

    const leaveGroupHandler = () => {
        leaveGroup({
            groupId: group.id,
            token: getToken(),
            refresh
        })
    }

    const deleteGroupHandler = () => {
        deleteGroup({groupId: group.id, token: getToken(), refresh})
    }

    const setNameHandler = () => {
        if (name != group.name && name.trim() != '') {
            setGroupName({groupId: group.id, token: getToken(), newName: name, setEditMode})
        }
    }

    return <Card className={styles.groupNode}>
        <div className={styles.wrapper}>
            <InputText
                id="group_name_input"
                placeholder="Моя новая группа"
                disabled={!editMode}
                className={styles.input}
                value={name}
                onChange = {e => setName(e.target.value)}
            />
            {editMode && <Button
                type="button"
                text="Сохранить"
                className={styles.setGroupName}
                onClick={() => setNameHandler()}
            />}
            {isOwner && <div className={styles.isOwner}>моя группа</div>}
        </div>
        <div className={styles.infoWrapper}>
            <div>
                <h5>Пользователи:</h5>
                <DataTable
                    key={editMode ? "edit" : "view"}
                    className={styles.table}
                    value={group.groupUsers.map(user => {
                    return {
                        userLogin: user.userLogin,
                        role: user.role,
                        btn: <div>{getBtn(user)}</div>
                    }
                })}>
                    <Column className={styles.tableNode} field="userLogin" header="Логин"/>
                    <Column className={styles.tableNode} field="role" header="Роль"/>
                    <Column className={styles.tableNode} field="btn" header=""/>
                </DataTable>
            </div>
            <div className={styles.services}>
                <h5>Сервисы:</h5>
                <div>
                    {group.services.map((service: GroupServiceModel) => {
                        return <ServiceNode
                            key={`service_${service.id}`}
                            service={service}
                            editMode={editMode}
                            groupId={group.id}
                            refresh={refresh}
                        />;
                    })}
                </div>
                {editMode && <SelectService
                    services={group.services}
                    groupId={group.id}
                    token={getToken()}
                    refresh={refresh}
                />}
            </div>
        </div>
        <Invitation groupId={group.id} token={getToken()} isOwner={isOwner} editMode={editMode}/>
        {isOwner && <div className={styles.adminBtn}>
            {editMode && <Button
                className={styles.deleteBtn}
                type="button"
                text="Удалить"
                onClick={deleteGroupHandler}
            />}
            <Button
                type="button"
                text={editMode ? "Отменить" : "Редактировать"}
                onClick={() => (setEditMode(prevState => !prevState))}/>
        </div>}
    </Card>
}

export default GroupNode;

/*
изменить дату
ввести приглашение
сортировка
ошибка при удалении
 */
