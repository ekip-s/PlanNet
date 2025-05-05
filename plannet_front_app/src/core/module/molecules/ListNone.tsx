import styles from "./ListNone.module.css"
import Card from "./card/Card.tsx";
import Button from "../atoms/btns/Button.tsx";
import {useNavigate} from "react-router";


interface ListNodeProps {
    title: string;
    description: string;
    inDevelopment: boolean;
    btnTitle: string;
    logoSvg: string;
    path:string;
}

const ListNone = ({title, description, inDevelopment, btnTitle, logoSvg, path}:ListNodeProps) => {
    const navigate = useNavigate();

    const redirectHandler = () => {
        navigate(path);
    }

    return <Card className={styles.card}>
        <div className={styles.descriptionModule}>
            <div>
                <h4 className={styles.title}>{title}</h4>
                <div className={styles.description}>{description}</div>
            </div>
            <div><img src={logoSvg} alt={title}/></div>
        </div>
        {!inDevelopment ? <div className={styles.wrapper}>
            <Button onClick={redirectHandler} type="button" text={btnTitle}/>
        </div> : <div></div>}
    </Card>
}

export default ListNone