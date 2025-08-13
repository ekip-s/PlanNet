import styles from "./Invitation.module.css"
import {useState} from "react";
import Button from "../../atoms/btns/Button.tsx";
import InvitationList from "./InvitationList.tsx";

interface InvitationProps {
    groupId: string;
    token: string | null;
    isOwner: boolean;
    editMode: boolean;
}

const Invitation = ({groupId, token, isOwner, editMode}:InvitationProps) => {
    const [display, setDisplay] = useState(false);

    if (!isOwner) return;

    if (display) {
        return <div className={styles.list}>
            <InvitationList groupId={groupId} token={token} editMode={editMode}/>
        </div>
    } else {
        return <div className={styles.invitation}>
            <Button
                type="button"
                text="Показать приглашения"
                onClick={() => setDisplay(prev => !prev)}
            />
        </div>

    }
}

export default Invitation;