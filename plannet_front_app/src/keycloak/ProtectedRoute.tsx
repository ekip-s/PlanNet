import {Outlet} from 'react-router-dom';
import { useAuth } from "./AuthContext";
import Loading from "../core/module/molecules/loading/Loading.tsx";
import {useEffect} from "react";

const ProtectedRoute = () => {
    const { isAuthenticated, login, loading } = useAuth();

    useEffect(() => {
        if (!loading && !isAuthenticated) {
            login();
        }
    }, [isAuthenticated, loading, login]);

    if (loading) {
        return <Loading />;
    }

    return isAuthenticated ? (<Outlet />) : (<Loading />);
};

export default ProtectedRoute;