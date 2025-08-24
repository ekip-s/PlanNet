import styles from "./Profile.module.css";
import {Outlet} from "react-router-dom";
import TabMenuSelect from "../atoms/tab_menu/TabMenuSelect.tsx";
import {useNavigate} from "react-router";

const Profile = () => {

    const navigate = useNavigate();

    const items = [
        {
            label: 'Основная страница',
            icon: 'pi pi-home',
            command: () => {navigate("/profile")}
        },
        {
            label: 'Группы',
            icon: 'pi pi-user-plus',
            command: () => {navigate("/profile/group")}
        },
        {
            label: 'Планы тренировок и питания',
            icon: 'pi pi-wave-pulse',
            command: () => {navigate("/profile/power")}
        },
    ];

    return <section className={styles.profile}>
        <TabMenuSelect items={items}/>
        <Outlet />
    </section>
}

export default Profile;
