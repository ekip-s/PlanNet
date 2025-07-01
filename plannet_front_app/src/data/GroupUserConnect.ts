import {send} from "../core/api/sendHTTP.tsx";

interface removeMemberProps {
    groupId: string;
    userId: string;
    token: string | null;
    refresh: () => void;
}

interface leaveGroupProps {
    groupId: string;
    token: string | null;
    refresh: () => void,
}

interface joinToGroupProps {
    code: string | undefined;
    token: string | null;
    toGroupNav: () => void;
    setError?: (error: string | '') => void
}

//удаление участника из группы
export const removeMember = ({groupId, userId, token, refresh}:removeMemberProps) => {
    send({
        url: `/${groupId}/user/${userId}`,
        service: "group_user",
        method: "DELETE",
        token: token,
        dataType: "not"
    }).then(() => {refresh()})
}

//Вход из группы
export const leaveGroup = ({groupId, token, refresh}:leaveGroupProps) => {
    send({
        url: `/${groupId}`,
        service: "group_user",
        method: "DELETE",
        token: token,
        dataType: "not"
    }).then(() => {refresh()})
}

//вступить в группу
export const joinToGroup = ({code, token, toGroupNav, setError}:joinToGroupProps) => {
    send({
        url: `/${code}`,
        service: "group_user",
        method: "POST",
        token: token,
        dataType: "not",
        setError
    })
        .then(() => {toGroupNav()})
}

