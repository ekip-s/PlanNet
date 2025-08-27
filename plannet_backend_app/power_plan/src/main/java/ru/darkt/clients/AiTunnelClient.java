package ru.darkt.clients;

import reactor.core.publisher.Mono;
import ru.darkt.models.balance.Balance;

public interface AiTunnelClient {
    Mono<Balance> getBalance(String token);
}
