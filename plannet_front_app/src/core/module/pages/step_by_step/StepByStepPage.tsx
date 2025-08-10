import style from "./StepByStepPage.module.css";
import Button from "../../atoms/btns/Button.tsx";
import {useNavigate} from "react-router";
import React, {useState} from "react";
import {Dropdown} from "primereact/dropdown";
import useApi from "../../../api/useApi.tsx";
import {TargetModel} from "../../../models/TargetModel.tsx";
import Loading from "../../molecules/loading/Loading.tsx";
import Error from "../../molecules/error/Error.tsx";
import TargetNode from "../../molecules/target/TargetNode.tsx";

const StepByStepPage = () => {

    const navigate = useNavigate();
    const [filter, setFilter] = useState({ name: 'Показать все', code: 'ALL' });
    const {data, loading, error} = useApi<TargetModel[]>({
        url: "/all",
        service: "target"
    });
    const filters = [
        { name: 'Показать все', code: 'ALL' },
        { name: 'Новые', code: 'NEW' },
        { name: 'Начатые', code: 'IN_PROGRESS' },
        { name: 'Завершенные', code: 'COMPLETED' },
        { name: 'Отмененные', code: 'CANCELED' },
    ]

    const filteredData = React.useMemo(() => {
        if (!data) return [];

        if (filter.code === 'ALL') {
            return data;
        }

        return data.filter(item => item.status === filter.code);
    }, [data, filter]);

    const newTargetHandler = () => {
        navigate("/stepByStep/new");
    }

    if (loading) {
        return <Loading />;
    }

    if (error) {
        return <Error message={"Ошибка загрузки данных"} />;
    }

    return <section className={style.stepByStepPage}>
        <div className={style.btnWrapper}>
            <div className={style.info}>
                <h3>Step By Step</h3>
                <p>Шаг за шагом двигайся к цели</p>
            </div>
            <div className={style.rightMobile}>
                <Dropdown
                    className={style.dropdown}
                    value={filter}
                    onChange={(e) => setFilter(e.value)}
                    options={filters}
                    optionLabel="name"
                />
                <Button type={"button"} text={"Новая цель"} onClick={newTargetHandler}/>
            </div>
        </div>
        <div className={style.nodeList}>
            {filteredData.map((item:TargetModel) => (<TargetNode key={item.id} target={item} />))}
        </div>
    </section>
}

export default StepByStepPage;