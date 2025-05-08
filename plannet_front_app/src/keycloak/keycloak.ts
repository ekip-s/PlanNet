import Keycloak from 'keycloak-js';

const keycloakConfig = {
    url: import.meta.env.VITE_KEYCLOAK_URL,
    realm: 'plan_net',
    clientId: 'plan_net_front_app',
};

const keycloak = new Keycloak(keycloakConfig);

export default keycloak;