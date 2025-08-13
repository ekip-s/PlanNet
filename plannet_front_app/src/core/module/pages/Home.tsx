import styles from "./Home.module.css"
import ListNone from "../molecules/ListNone.tsx";
import money_logo from "../../../assets/money.svg";
import step_logo from "../../../assets/step.svg";
import new_logo from "../../../assets/new.svg";
import nginx from "../../../assets/mginx.svg";
import task_logo from "../../../assets/task.svg";
import keycloak_logo from "../../../assets/keycloak.svg";
import HorizontalScroll from "../templates/scroll/HorizontalScroll.tsx";
import TechnologiesNode from "../molecules/TechnologiesNode.tsx";

export interface ListNodeProps {
    id: number;
    title: string;
    inDevelopment: boolean;
    hidden: boolean;
    path: string;
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
            {appList
                .filter((node: ListNodeProps)=> !node.hidden)
                .map((node: ListNodeProps, index: number) => {
                return <ListNone key={`list_node_${index}`}
                                 title={node.title}
                                 description={node.description}
                                 inDevelopment={node.inDevelopment}
                                 btnTitle={node.btnTitle}
                                 logoSvg={node.logoSvg}
                                 path={node.path}
                />
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
    {
        id: 2,
        title: "Keycloak",
        description: "Система управления идентификацией и доступом",
        logoSvg: keycloak_logo,
    },
]

const appList:ListNodeProps[] = [
    {
        id: 1,
        title: "Task Master",
        inDevelopment: false,
        hidden: false,
        btnTitle: "Начать ...",
        logoSvg: task_logo,
        description: "Планируйте, организуйте и контролируйте рабочие процессы в одном месте.",
        path: "/tasks"
    },
    {
        id: 2,
        title: "Coin Keeper",
        inDevelopment: false,
        hidden: true,
        btnTitle: "Начать ...",
        logoSvg: money_logo,
        description: "Умный учет финансов. Всегда знайте, куда уходят деньги!",
        path: ""
    },
    {
        id: 3,
        title: "Step by Step",
        inDevelopment: false,
        hidden: false,
        btnTitle: "К цели ...",
        logoSvg: step_logo,
        description: "Ведите к своим большим целям через пошаговые действия и следите за эффективностью.",
        path: "/stepByStep"
    },
    {
        id: 4,
        title: "Тут будет новый сервис",
        inDevelopment: true,
        hidden: false,
        btnTitle: "Перейти ...",
        logoSvg: new_logo,
        description: "Пока еще не придумал",
        path: ""
    },
]