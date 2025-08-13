import styles from "./SelectNode.module.css"
import {Dropdown} from "primereact/dropdown";

interface InputTextProps<T> {
    placeholder: string;
    selected: T;
    setSelected: (selected: T) => void;
    options: OptionType<T>[];
}

interface OptionType<T> {
    label: string;
    value: T;
}

const SelectNode = <T,>({placeholder, selected, setSelected, options} : InputTextProps<T>) => {
    return <Dropdown
        placeholder={placeholder}
        value={selected}
        onChange={e => setSelected(e.value)}
        options={options}
        className={styles.customDropdown}
        panelClassName={styles.customDropdownPanel}
        itemTemplate={itemTemplate}
    />
}

const itemTemplate = <T,>(option: OptionType<T>) => {
    return (
        <div className={styles.node}>
            {option.label}
        </div>
    );
};

export default SelectNode;