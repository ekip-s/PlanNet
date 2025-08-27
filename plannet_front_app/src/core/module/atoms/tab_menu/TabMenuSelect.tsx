import {TabMenu} from "primereact/tabmenu";

interface TabMenuSelectProps {
    items: ItemsProps[];
    activeIndex: number
}

interface ItemsProps {
    label: string;
    icon: string;
    command: () => void;
}


const TabMenuSelect = ({ items, activeIndex }: TabMenuSelectProps) => {


    return <TabMenu model={items} pt={tabMenuPT} activeIndex={activeIndex}/>
}

export default TabMenuSelect;

const tabMenuPT = {
    root: { className: 'global-custom-root' },
    menu: { className: 'global-custom-menu ' },
    menuitem: { className: 'menu-item' },
    action: { className: 'menu-action' },
    icon: { className: 'menu-icon' },
    label: { className: 'menu-label' },
};

