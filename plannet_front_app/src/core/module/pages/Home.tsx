import styles from "./Home.module.css"
import ListNone from "../molecules/ListNone.tsx";
import money_logo from "../../../assets/money.svg";
import new_logo from "../../../assets/new.svg";
import nginx from "../../../assets/mginx.svg"
import HorizontalScroll from "../templates/scroll/HorizontalScroll.tsx";
import TechnologiesNode from "../molecules/TechnologiesNode.tsx";

export interface ListNodeProps {
    id: number;
    title: string;
    inDevelopment: boolean;
    description: string;
    btnTitle: string;
    logoSvg: string;
}

export interface TechnologiesListProps {
    id: number;
    title: string;
    description: string;
    logoSvg: string;
}

const Home = () => {
    return <div className={styles.home}>
        <h3>Сервисы:</h3>
        <div className={styles.serviceList}>
            {appList.map((node: ListNodeProps, index: number) => {
                return <ListNone key={`list_node_${index}`}
                                 title={node.title}
                                 description={node.description}
                                 inDevelopment={node.inDevelopment}
                                 btnTitle={node.btnTitle}
                                 logoSvg={node.logoSvg}/>
            })}
        </div>
        <hr />
        <h3>В приложении используются:</h3>
        <div className={styles.technologiesList}>
            <HorizontalScroll items={technologiesList}
                              renderItem={(item) =>
                                  (<TechnologiesNode
                                  title={item.title}
                                  description={item.description}
                                  logoSvg={item.logoSvg} />)}
                              keyExtractor={(item) =>
                                  `technologies_node_${item.id}`} />
        </div>
    </div>
}

export default Home;

const technologiesList:TechnologiesListProps[] = [
    {
        id: 1,
        title: "nginx",
        description: "HTTP-сервер, обратный прокси сервер с поддержкой кеширования и балансировки нагрузки",
        logoSvg: nginx,
    },
]

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