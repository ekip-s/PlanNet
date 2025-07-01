import {send} from "../core/api/sendHTTP.tsx";

interface addNewGroupProps {
    token: string | null,
    refresh: () => void,
}

interface setGroupNameProps {
    groupId: string,
    token: string | null,
    newName: string,
    setEditMode: (value: boolean) => void,
}

interface deleteGroupProps {
    groupId: string,
    token: string | null,
    refresh: () => void,
}

//создает новую, пустую группу
export const addNewGroup = ({token,refresh} : addNewGroupProps ) => {
    send({
        url: "",
        service: "group",
        method: "POST",
        body: {
            name: "Самая новая группа",
            services: []
        },
        token: token,
    })
        .then(() => {refresh()})
}

//обновление name группы
export const setGroupName = ({groupId, token, newName, setEditMode}: setGroupNameProps) => {
    send({
        url: "",
        service: "group",
        method: "POST",
        body: {
            id: groupId,
            name: newName,
            services: []
        },
        token: token,
    })
        .then(() => {setEditMode(false)})
}

//удаление группы
export const deleteGroup = ({groupId, token, refresh}:deleteGroupProps) => {
    send({
        url: `/${groupId}`,
        service: "group",
        method: "DELETE",
        token: token,
    }).then(() => {refresh()})
}