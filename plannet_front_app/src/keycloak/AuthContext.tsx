import React, {
    createContext,
    useCallback,
    useContext,
    useEffect,
    useRef,
    useState,
} from 'react';
import keycloak from './keycloak';
import Loading from "../core/module/molecules/loading/Loading.tsx";

interface User {
    id: string;
    username: string;
    email: string;
    firstName: string;
    lastName: string;
    token: string;
    roles: string[];
}

interface AuthContextType {
    isAuthenticated: boolean;
    user: User | null;
    keycloak: typeof keycloak;
    logout: () => void;
    loading: boolean;
    getToken: () => string | null;
    getUserId: () => string | null;
    getUsername: () => string | null;
    getUserRoles: () => string[];
}

const AuthContext = createContext<AuthContextType | null>(null);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({
                                                                          children,
                                                                      }) => {
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [user, setUser] = useState<User | null>(null);
    const [loading, setLoading] = useState(true);
    const isInitialized = useRef(false);
    const tokenRefreshInterval = useRef<number | undefined>(undefined); // Для хранения интервала обновления токена

    useEffect(() => {
        if (!isInitialized.current) {
            isInitialized.current = true;

            keycloak
                .init({ onLoad: 'login-required' })
                .then((authenticated) => {
                    if (authenticated) {
                        const updateUserData = () => {
                            const userData: User = {
                                id: keycloak.subject || '',
                                username: keycloak.tokenParsed?.preferred_username || '',
                                email: keycloak.tokenParsed?.email || '',
                                firstName: keycloak.tokenParsed?.given_name || '',
                                lastName: keycloak.tokenParsed?.family_name || '',
                                token: keycloak.token || '',
                                roles: keycloak.tokenParsed?.realm_access?.roles || [],
                            };
                            setUser(userData);
                        };

                        // Первоначальная установка данных пользователя
                        updateUserData();
                        setIsAuthenticated(true);

                        // Настройка периодического обновления токена
                        tokenRefreshInterval.current = window.setInterval(() => {
                            keycloak
                                .updateToken(70) // Обновить если осталось меньше 70 секунд
                                .then((refreshed) => {
                                    if (refreshed) {
                                        console.log('Token refreshed');
                                        updateUserData(); // Обновляем данные пользователя с новым токеном
                                    }
                                })
                                .catch((error) => {
                                    console.error('Failed to refresh token:', error);
                                    logout(); // При ошибке обновления разлогиниваем
                                });
                        }, 60000); // Проверка каждую минуту
                    }
                    setLoading(false);
                })
                .catch((error) => {
                    console.error('Keycloak init error:', error);
                    setLoading(false);
                });
        }

        // Очистка при размонтировании
        return () => {
            if (tokenRefreshInterval.current) {
                clearInterval(tokenRefreshInterval.current);
            }
        };
    }, []);

    const logout = () => {
        if (tokenRefreshInterval.current) {
            clearInterval(tokenRefreshInterval.current);
        }
        keycloak.logout();
        setIsAuthenticated(false);
        setUser(null);
    };

    const getToken = useCallback(() => user?.token || null, [user]);
    const getUserId = useCallback(() => user?.id || null, [user]);
    const getUsername = useCallback(() => user?.firstName || null, [user]);
    const getUserRoles = useCallback(() => user?.roles || [], [user]);

    if (loading) {
        return <Loading />;
    }

    return (
        <AuthContext.Provider
            value={{
                isAuthenticated,
                user,
                keycloak,
                logout,
                loading,
                getToken,
                getUserId,
                getUsername,
                getUserRoles,
            }}
        >
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
};