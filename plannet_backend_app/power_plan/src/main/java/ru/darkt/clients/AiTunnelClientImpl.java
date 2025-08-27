package ru.darkt.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.darkt.models.balance.Balance;

@Service
public class AiTunnelClientImpl implements AiTunnelClient {

    private final WebClient webClient;
    @Value("${service.uri.aitunnel}")
    private String BASE_URL;

    @Autowired
    public AiTunnelClientImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<Balance> getBalance(String token) {

        return ((WebClient.RequestHeadersSpec<?>)webClient
                .get()
                .uri(BASE_URL + "/v1/aitunnel/balance"))
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(Balance.class);
    }
}
