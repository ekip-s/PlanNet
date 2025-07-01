import {send} from "../core/api/sendHTTP.tsx";


interface deleteServiceByGroupProps {
    groupId: string;
    serviceName: string;
    token: string | null;
    refresh: () => void;
    setLoading?: (loading: boolean) => void;
    setError?: (error: string) => void;
}

interface addNewServiceToGroupProps {
    groupId: string;
    serviceName: string;
    token: string | null;
    refresh: () => void;
}

//удалить сервис из группы
export const deleteServiceByGroup = (
    {groupId,
        serviceName,
        token,
        setLoading,
        setError,
        refresh
    } : deleteServiceByGroupProps) => {

    send({
        url: `/${groupId}?service=${serviceName}`,
        service: "group_service",
        method: "DELETE",
        token: token,
        setLoading: setLoading,
        setError: setError,
        dataType: "not"
    }).then(() => {refresh()})
}

//добавим сервис к группе
export const addNewServiceToGroup = (
    {
        groupId,
        serviceName,
        token,
        refresh
    }:addNewServiceToGroupProps) => {
    send({
        url: `/${groupId}?service=${serviceName}`,
        service: "group_service",
        method: "POST",
        token: token,
    })
        .then(() => {refresh()})
}