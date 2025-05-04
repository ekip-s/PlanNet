import styles from "./Home.module.css"
import ListNone from "../molecules/ListNone.tsx";
import money_logo from "../../../assets/money.svg";
import new_logo from "../../../assets/new.svg";

export interface ListNodeProps {
    id: number;
    title: string;
    inDevelopment: boolean;
    description: string;
    btnTitle: string;
    logoSvg: string;
}

const appList:ListNodeProps[] = [
    {
        id: 1,
        title: "Coin Keeper",
        inDevelopment: false,
        btnTitle: "Начать ...",
        logoSvg: money_logo,
        description: "Умный учет финансов. Всегда знайте, куда уходят деньги!"
    },
    {
        id: 2,
        title: "Тут будет новый сервис",
        inDevelopment: true,
        btnTitle: "Перейти ...",
        logoSvg: new_logo,
        description: "Пока еще не придумал"
    },
]

const Home = () => {
    return <div className={styles.styles}>
        <h3>Сервисы:</h3>
        <div className={styles.serviceList}>
            {appList.map((node:ListNodeProps, index: number) =>
            {
                return <ListNone key={`list_node_${index}`}
                                 title={node.title}
                                 description={node.description}
                                 inDevelopment={node.inDevelopment}
                                 btnTitle={node.btnTitle}
                                 logoSvg={node.logoSvg}/>
            })}
        </div>
    </div>
}

export default Home;