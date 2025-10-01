package com.example.faturamento.service;

import com.example.faturamento.model.Pedido;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@Service
public class FaturamentoService {

    // Lista thread-safe para armazenar os pedidos processados
    private final List<Pedido> pedidosFaturados = new CopyOnWriteArrayList<>();

    // Bean que atua como o consumidor de mensagens (Consumer)
    @Bean
    public Consumer<Pedido> processarPedidoFaturamento() {
        return pedido -> {
            System.out.println(" [x] Recebido evento NovoPedidoCriado. Processando faturamento para: " + pedido.toString());
            // Lógica de negócio para processar o pagamento
            System.out.println(" [x] Faturamento do pedido " + pedido.getId() + " processado com sucesso!");
            
            // Adiciona o pedido à lista para ser exibido na UI
            pedidosFaturados.add(pedido);
        };
    }

    // Método para o controller obter a lista de pedidos faturados
    public List<Pedido> getPedidosFaturados() {
        return Collections.unmodifiableList(pedidosFaturados);
    }
}