import styles from "./PaginationList.module.css";
import {Paginator, PaginatorPageChangeEvent} from 'primereact/paginator';
import {useState} from "react";
import useApi from "../../../api/useApi.tsx";
import {PageTargetModel, TargetModel} from "../../../models/TargetModel.tsx";
import {useSelector} from "react-redux";
import {RootState} from "../../../../store/store.ts";
import Loading from "../../molecules/loading/Loading.tsx";
import Error from "../../molecules/error/Error.tsx";
import Action from "../../molecules/action/Action.tsx";

const PaginationList = () => {

    const [first, setFirst] = useState<number>(0);
    const size = 10;
    const id = useSelector((state: RootState) => state.form.id);
    const visible = useSelector((state: RootState) => state.form.state);
    const {data, loading, error} = useApi<PageTargetModel>({
        url: `/${id}/action?page=${first/size}&size=${size}&sort=createdAt%2Casc`,
        service: "target",
        method: "GET",
        visible
    });

    const onPageChange = (event: PaginatorPageChangeEvent) => {
        setFirst(event.first);
    };

    if (loading) {
        return <Loading />;
    }

    if (error) {
        return <Error message={`Ошибка запроса данных`} />;
    }

    if(!data ||
        !('_embedded' in data) ||
        !Array.isArray((data as PageTargetModel)._embedded.targetResponseList) ||
        (data as PageTargetModel)._embedded.targetResponseList.length === 0) {
        return <Error message={`Еще нет действий, создайте их на карточке задачи, нажав "+"`}/>;
    }

    return <section className={styles.paginationList}>
        <div className={styles.nodeMode}>
            {data._embedded.targetResponseList.map((action: TargetModel) => {
                return <Action
                    key={`action_node_${action.id}`}
                    action={action}
                />
            })}
        </div>
        <div>{first}</div>
        <Paginator first={first} rows={size} totalRecords={data.page.totalElements} onPageChange={onPageChange}
                   template={{ layout: 'PrevPageLink CurrentPageReport NextPageLink' }} />
    </section>
}

export default PaginationList;


//http://localhost:8083/target/api/v1/9b50bf92-15b8-46ab-80de-aad1eacba029/action?page=0&size=20&sort=createdAt%2Casc