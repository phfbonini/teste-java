package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Venda {
    private int id;
    private Date data;
    private int clienteId;
    private double valorTotal;
    private String status;
    private List<ItemVenda> itensVenda;

    public Venda(int id, Date data, int clienteId, double valorTotal, String status) {
        this.id = id;
        this.data = data;
        this.clienteId = clienteId;
        this.valorTotal = valorTotal;
        this.status = status;
        this.itensVenda = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public Date getData() {
        return data;
    }

    public int getClienteId() {
        return clienteId;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public String getStatus() {
        return status;
    }

    public List<ItemVenda> getItensVenda() {
        return itensVenda;
    }

    public void adicionarItem(ItemVenda item) {
        itensVenda.add(item);
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Data: " + data + ", ClienteID: " + clienteId + ", Valor Total: " + valorTotal + ", Status: " + status;
    }

    public void setStatus(String Status) {
        this.status = Status;
    }
}
