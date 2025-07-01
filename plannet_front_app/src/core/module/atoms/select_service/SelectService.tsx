import styles from "./SelectService.module.css"
import Button from "../btns/Button.tsx";
import {GroupServiceModel} from "../../../models/GroupModel.tsx";
import localData from "./../../../../core/api/localData.json"
import {useState} from "react";
import {addNewServiceToGroup} from "../../../../data/ServiceConnect.ts";
import SelectNode from "../input_text/SelectNode.tsx";

interface SelectServiceProps {
    services: GroupServiceModel[],
    groupId: string,
    token: string | null,
    refresh: () => void,
}

const SelectService = ({services, groupId, token, refresh}: SelectServiceProps) => {
    const excludedNames = services.map(service => service.name);
    const availableServices = localData.services
        .filter(service => !excludedNames.includes(service))
        .map(serviceName => ({
            label: serviceName,
            value: serviceName,
        }));
    const [selectedService, setSelectedService] = useState<string>("");

    const addNewServiceToGroupHandler = () => {
        if (selectedService) {
            addNewServiceToGroup(
                {
                    groupId: groupId,
                    serviceName: selectedService,
                    token: token,
                    refresh: refresh,
                });
        }
    }

    return <div className={styles.selectService}>
        {availableServices.length != 0 && <div>
            <SelectNode<string>
                placeholder="Сервис"
                selected={selectedService}
                setSelected={setSelectedService}
                options={availableServices}
            />
            <Button
                type="button"
                text="Добавить"
                className={styles.btn} onClick={addNewServiceToGroupHandler}
            />
        </div>}
    </div>
}

export default SelectService;


