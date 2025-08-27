import styles from "./SelectNode.module.css"
import {Dropdown} from "primereact/dropdown";

interface InputTextProps<T> {
    id?: string;
    optionLabel?: string;
    placeholder: string;
    selected: T;
    setSelected: (selected: T) => void;
    options: OptionType[];
}

export interface OptionType {
    name?: string;
    code: string | number;
    [key: string]: unknown;
}

const SelectNode = <T,>({
                            id,
                            optionLabel = "name",
                            placeholder,
                            selected,
                            setSelected,
                            options
} : InputTextProps<T>) => {

    const itemTemplate = (option: OptionType) => {

        const label = option[optionLabel];

        return (
            <div className={styles.node}>
                {typeof label === "string" ? label : String(label)}
            </div>
        );
    };

    return <Dropdown
        id={id}
        placeholder={placeholder}
        value={selected}
        onChange={e => setSelected(e.value)}
        options={options}
        className={styles.customDropdown}
        panelClassName={styles.customDropdownPanel}
        itemTemplate={itemTemplate}
    />
}

export default SelectNode;