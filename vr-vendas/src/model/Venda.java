package model;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Venda {
    private int id;
    private Date data;
    private Cliente cliente;
    private double valorTotal;
    private String status;
    private List<Produto> produtos;

    public Venda(int id, Date data, Cliente cliente, double valorTotal, String status) {
        this.id = id;
        this.data = data;
        this.cliente = cliente;
        this.valorTotal = valorTotal;
        this.status = status;
        this.produtos = new ArrayList<>();
    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "id=" + id +
                ", data=" + data +
                ", cliente=" + cliente +
                ", valorTotal=" + valorTotal +
                ", status='" + status + '\'' +
                '}';
    }
}
