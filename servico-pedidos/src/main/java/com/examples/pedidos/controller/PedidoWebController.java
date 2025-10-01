package com.example.pedidos.controller;

import com.example.pedidos.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PedidoWebController {

    @Autowired
    private StreamBridge streamBridge;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("pedido", new Pedido());
        return "index"; // Retorna o nome do arquivo HTML (index.html)
    }

    @PostMapping("/criar-pedido")
    public String criarPedido(@ModelAttribute Pedido pedido) {
        System.out.println("Recebido novo pedido via formulário: " + pedido.getId());
        
        // Envia o evento para o RabbitMQ usando o binding configurado
        streamBridge.send("publicarNovoPedido-out-0", pedido);
        
        return "redirect:/sucesso";
    }

    @GetMapping("/sucesso")
    public String sucesso() {
        return "sucesso"; // Retorna a página de sucesso (sucesso.html)
    }
}