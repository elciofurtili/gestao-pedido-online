package com.example.faturamento.controller;

import com.example.faturamento.service.FaturamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FaturamentoWebController {

    @Autowired
    private FaturamentoService faturamentoService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("pedidos", faturamentoService.getPedidosFaturados());
        return "faturamento"; // Retorna o nome do arquivo faturamento.html
    }
}