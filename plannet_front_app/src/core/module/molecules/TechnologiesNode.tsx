import styles from "./TechnologiesNode.module.css"
import Card from "./card/Card.tsx";


interface TechnologiesNodeProps {
    title: string;
    description: string;
    logoSvg: string;
}

const TechnologiesNode = ({title, description, logoSvg}:TechnologiesNodeProps) => {
    return <Card className={styles.technologiesNode}>
        <div className={styles.wrapper}>
            <div>
                <h5 className={styles.title}>{title}</h5>
                <div className={styles.description}>{description}</div>
            </div>
            <div><img className={styles.img} src={logoSvg} alt={title}/></div>
        </div >

    </Card>
}

export default TechnologiesNode;