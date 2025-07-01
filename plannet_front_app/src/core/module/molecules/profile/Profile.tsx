import styles from './Profile.module.css'
import {useEffect, useRef, useState} from "react";
import {useNavigate} from "react-router";
import {useAuth} from "../../../../keycloak/AuthContext.tsx";
import profile_logo from "../../../../assets/profile.svg"

const Profile = () => {
    const [isMenuOpen, setIsMenuOpen] = useState(false);
    const navigate = useNavigate();
    const containerRef = useRef<HTMLDivElement>(null);
    const { logout, isAuthenticated, getUsername } = useAuth();

    const toggleMenu = () => setIsMenuOpen((prev) => !prev);
    const closeMenu = () => setIsMenuOpen(false);

    useEffect(() => {
        const handleClickOutside = (event: MouseEvent) => {
            if (
                containerRef.current &&
                !containerRef.current.contains(event.target as Node)
            ) {
                closeMenu();
            }
        };

        document.addEventListener('mousedown', handleClickOutside);
        return () => document.removeEventListener('mousedown', handleClickOutside);
    }, []);

    useEffect(() => {
        const handleKeyDown = (event: KeyboardEvent) => {
            if (event.key === 'Escape') closeMenu();
        };

        document.addEventListener('keydown', handleKeyDown);
        return () => document.removeEventListener('keydown', handleKeyDown);
    }, []);

    const profileLogoutHandler = () => {
        navigate('/logout');
        logout();
    };

    const goToPageHandler = (page:string) => {
        navigate(page);
    }

    if (!isAuthenticated) return;

    return (
        <div className={styles.profileContainer} ref={containerRef}>
            <button
                className={styles.profileBtn}
                onClick={toggleMenu}
                aria-expanded={isMenuOpen}
                aria-controls="profile-menu"
                type="button"
            >
                <img
                    src={profile_logo}
                    alt="Аватар профиля"
                    className={styles.profileImage}
                />
                <p className={styles.username}>{getUsername()}</p>
            </button>
            <div
                id="profile-menu"
                className={`${styles.profileMenu} ${
                    isMenuOpen ? styles.menuVisible : ''
                }`}
                role="menu"
            >
                <div className={styles.menuItem} role="menuitem">
                    <button className={styles.node} onClick={() => goToPageHandler("profile")}>
                        Профиль
                    </button>
                    <hr />
                    <button className={styles.logout} onClick={profileLogoutHandler}>
                        Выйти
                    </button>
                </div>
            </div>
        </div>
    );
}

export default Profile;