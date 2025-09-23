package com.example.pedidos.controller;

import com.example.pedidos.model.Pedido;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Sinks;
import java.util.function.Supplier;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final Sinks.Many<Pedido> pedidoSink = Sinks.many().multicast().onBackpressureBuffer();

    @PostMapping
    public String criarPedido(@RequestBody Pedido pedido) {
        System.out.println("Recebido novo pedido: " + pedido.getId());
        // Emite o evento para o broker
        pedidoSink.tryEmitNext(pedido);
        return "Pedido " + pedido.getId() + " recebido e enviado para processamento!";
    }

    // Bean que atua como o produtor de mensagens (Supplier)
    @Bean
    public Supplier<Pedido> publicarNovoPedido() {
        return () -> pedidoSink.asFlux().doOnNext(p -> System.out.println("Publicando evento NovoPedidoCriado: " + p.getId())).blockFirst();
    }
}