import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from "./AuthContext";
import Loading from "../core/module/molecules/loading/Loading.tsx";

const ProtectedRoute = () => {
    const { isAuthenticated, loading } = useAuth();

    if (loading) {
        return <Loading />;
    }

    return isAuthenticated ? <Outlet /> : <Navigate to="/login" />;
};

export default ProtectedRoute;