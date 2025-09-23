package com.example.faturamento.model;

import java.math.BigDecimal;

public class Pedido {
    private Long id;
    private String nomeCliente;
    private BigDecimal valor;

    // Getters e Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNomeCliente() {
        return nomeCliente;
    }
    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
    public BigDecimal getValor() {
        return valor;
    }
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", nomeCliente='" + nomeCliente + '\'' +
                ", valor=" + valor +
                '}';
    }
}