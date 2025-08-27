import styles from "./Profile.module.css";
import {Outlet, useLocation} from "react-router-dom";
import TabMenuSelect from "../atoms/tab_menu/TabMenuSelect.tsx";
import {useNavigate} from "react-router";

const Profile = () => {

    const navigate = useNavigate();
    const location = useLocation();

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

    const pathMap = ['/profile', '/profile/group', '/profile/power']

    const activeIndex = pathMap.findIndex(path => location.pathname === path);

    return <section className={styles.profile}>
        <TabMenuSelect items={items} activeIndex={activeIndex === -1 ? 0 : activeIndex}/>
        <Outlet />
    </section>
}

export default Profile;


