import styles from "./Profile.module.css";
import {Outlet} from "react-router-dom";
import TabMenuSelect from "../atoms/tab_menu/TabMenuSelect.tsx";
import {useNavigate} from "react-router";

const Profile = () => {

    const navigate = useNavigate();

    const items = [
        { label: 'Основная страница', icon: 'pi pi-home', command: () => {navigate("/profile")} },
        { label: 'Управление группами', icon: 'pi pi-home', command: () => {navigate("/profile/group")} },
    ];

    return <section className={styles.profile}>
        <TabMenuSelect items={items}/>
        <Outlet />
    </section>
}

export default Profile;
