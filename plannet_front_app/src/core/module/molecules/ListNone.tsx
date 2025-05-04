import styles from "./ListNone.module.css"
import Card from "./card/Card.tsx";
import Button from "../atoms/btns/Button.tsx";


interface ListNodeProps {
    title: string;
    description: string;
    inDevelopment: boolean;
    btnTitle: string;
    logoSvg: string;
}

const ListNone = ({title, description, inDevelopment, btnTitle, logoSvg}:ListNodeProps) => {

    return <Card className={styles.card}>
        <h4 className={styles.title}>{title}</h4>
        <div className={styles.descriptionModule}>
            <div className={styles.description}>{description}</div>
            <div><img src={logoSvg} alt={title}/></div>
        </div>
        {!inDevelopment ? <div className={styles.wrapper}>
            <Button type="button" text={btnTitle}/>
        </div> : <div></div>}
    </Card>
}

export default ListNone