import React, {
    useEffect,
    useState,
    useRef,
    useCallback,
} from 'react';
import { useAuth } from './../../keycloak/AuthContext';
import apiNavigation from "./apiNavigation.json"

type HttpMethod = 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE';

interface ApiConfig {
    url?: string;
    service: keyof typeof apiNavigation;
    method?: HttpMethod;
    body?: object | null;
    headers?: Record<string, string>;
}

interface UseApiResponse<T> {
    data: T | [];
    loading: boolean;
    error: Error | null;
    setData: React.Dispatch<React.SetStateAction<T | []>>;
    refresh: () => void;
}

const useApi = <T = unknown,>({
                                  url = "",
                                  service,
                                  method = 'GET',
                                  body = null,
                                  headers = {},
                              }: ApiConfig): UseApiResponse<T> => {
    const { getToken } = useAuth();
    const [data, setData] = useState<T | []>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<Error | null>(null);
    const isMounted = useRef(true);

    // Создаем стабильные рефы для значений, которые могут меняться
    const stableBody = useRef(body);
    const stableHeaders = useRef(headers);
    const stableGetToken = useRef(getToken);

    // Обновляем рефы при изменении значений
    useEffect(() => {
        stableBody.current = body;
        stableHeaders.current = headers;
        stableGetToken.current = getToken;
    }, [body, headers, getToken]);

    const fetchData = useCallback(async (controller: AbortController) => {
        try {
            setLoading(true);
            setError(null);

            const token = stableGetToken.current();
            const authHeader = token ? { Authorization: `Bearer ${token}` } : {};

            const cleanHeaders = Object.entries({
                'Content-Type': 'application/json',
                ...stableHeaders.current,
                ...authHeader
            }).reduce((acc, [key, value]) => {
                if (value !== undefined) {
                    acc[key] = value;
                }
                return acc;
            }, {} as Record<string, string>);

            const requestBody = stableBody.current ? JSON.stringify(stableBody.current) : null;

            const response = await fetch(
                getPath(service, url),
                {
                    method,
                    headers: cleanHeaders,
                    body: requestBody,
                    signal: controller.signal,
                }
            );

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const result = await response.json() as T;
            if (isMounted.current) {
                setData(result);
            }
        } catch (err) {
            if (isMounted.current && !controller.signal.aborted) {
                setError(err instanceof Error ? err : new Error('Unknown error'));
            }
        } finally {
            if (isMounted.current) {
                setLoading(false);
            }
        }
    }, [url, service, method]);

    useEffect(() => {
        const controller = new AbortController();
        isMounted.current = true;

        fetchData(controller);

        return () => {
            isMounted.current = false;
            controller.abort();
        };
    }, [fetchData]);

    const refresh = useCallback(() => {
        const controller = new AbortController();
        fetchData(controller);
        return () => controller.abort();
    }, [fetchData]);

    return { data, loading, error, setData, refresh };
};

export default useApi;

export const getPath = (service: keyof typeof apiNavigation, url: string) => {
    const serviceConfig = apiNavigation[service];
    const baseUrl = import.meta.env[serviceConfig.baseUrl];
    return `${baseUrl}${serviceConfig.path}${url}`;
}