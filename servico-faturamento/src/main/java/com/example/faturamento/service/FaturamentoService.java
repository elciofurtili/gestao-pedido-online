package com.example.faturamento.service;

import com.example.faturamento.model.Pedido;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class FaturamentoService {

    // Bean que atua como o consumidor de mensagens (Consumer)
    @Bean
    public Consumer<Pedido> processarPedidoFaturamento() {
        return pedido -> {
            // Simula o processamento do pagamento
            System.out.println(" [x] Recebido evento NovoPedidoCriado. Processando faturamento para: " + pedido.toString());
            // Aqui iria a lógica de negócio real para processar o pagamento
            System.out.println(" [x] Faturamento do pedido " + pedido.getId() + " processado com sucesso!");
        };
    }
}