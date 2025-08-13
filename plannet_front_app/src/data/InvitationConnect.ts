import {send} from "../core/api/sendHTTP.tsx";

interface addInvitationProps {
    groupId: string;
    token: string | null;
    refresh: () => void;
}

interface deleteInvitationProps {
    code: string;
    token: string | null;
    refresh: () => void;
}

//создаем новое приглашение в группе
export const addInvitation = ({groupId, token, refresh}:addInvitationProps) => {
    send({
        url: `/${groupId}`,
        service: "invitation",
        method: "POST",
        token: token,
        dataType: "not"
    }).then(() => {refresh()})
}

//удаление приглашения по коду
export const deleteInvitation = ({code, token, refresh}:deleteInvitationProps) => {
    send({
        url: `/${code}`,
        service: "invitation",
        method: "DELETE",
        token: token,
        dataType: "not"
    }).then(() => {refresh()})
}