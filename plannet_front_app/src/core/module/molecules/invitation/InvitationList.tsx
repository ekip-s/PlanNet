import useApi from "../../../api/useApi.tsx";
import styles from "./InvitationList.module.css"
import {InvitationModel} from "../../../models/InvitationModel.ts";
import Loading from "../loading/Loading.tsx";
import Error from "../error/Error.tsx";
import Button from "../../atoms/btns/Button.tsx";
import {addInvitation, deleteInvitation} from "../../../../data/InvitationConnect.ts";
import {useAuth} from "../../../../keycloak/AuthContext.tsx";

interface InvitationListProps {
    groupId: string;
    token: string | null;
    editMode: boolean;
}

const InvitationList = ({groupId, token, editMode}: InvitationListProps) => {
    const {data, loading, error, refresh} = useApi<InvitationModel[]>({
        url: `/${groupId}`,
        service: "invitation",
    })

    if (loading) {
        return <Loading />
    }

    if (error) {
        return <Error message="Ошибка при загрузке приглашений"/>
    }

    const addInvitationHandler = () => {
        addInvitation({
            groupId: groupId,
            token: token,
            refresh
        })
    }

    return <div className={styles.invitationList}>
        <h5>Приглашения:</h5>
        <p className={styles.comment}>Отправь ссылку другу или коллеге, чтобы он присоединился к группе</p>
        <div>
            {data.map((invitation:InvitationModel) => {
                return <div key={`invitation_code_${invitation.code}`}>
                    <TextWithCopy code={invitation.code} editMode={editMode} refresh={refresh}/>
                </div>
            })}
        </div>
        {editMode && <Button
            text="Новое приглашение"
            type='button'
            className={styles.addInvitationBtn}
            onClick={addInvitationHandler}/>}
    </div>
}

export default InvitationList;

interface TextWithCopyProps {
    code: string;
    editMode: boolean;
    refresh: () => void;
}

const TextWithCopy = ({ code, editMode, refresh }:TextWithCopyProps) => {
    const url = `${window.location.href}/join/${code}`;
    const {getToken} = useAuth();

    const deleteInvitationHandler = () => {
        deleteInvitation({
            code,
            token: getToken(),
            refresh
        })
    }

    return <div className={styles.textWithCopy}>
        <div className={styles.data}>
            <p><label>Ссылка:</label>{url}</p>
        </div>
        <div className={styles.info}>
            <span>Создано: 06.06.2025</span>
            <div>
                <button onClick={() => copyTextToClipboard(url)}>
                    <i className="pi pi-copy"/>
                </button>
                {editMode && <button onClick={deleteInvitationHandler}>
                    <i className="pi pi-trash"/>
                </button>}
            </div>
        </div>
    </div>
}

const copyTextToClipboard = (text: string) => {
    if (navigator.clipboard) {
        navigator.clipboard.writeText(text)
            .then(() => {
                console.log('Текст успешно скопирован');
            })
            .catch(err => {
                console.error('Ошибка копирования текста:', err);
            });
    }
}
