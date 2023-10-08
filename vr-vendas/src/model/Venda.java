package model;

import java.util.Date;

public class Venda {
    private int id;
    private Date data;
    private int clienteId;
    private double valorTotal;
    private String status;

    public Venda(int id, Date data, int clienteId, double valorTotal, String status) {
        this.id = id;
        this.data = data;
        this.clienteId = clienteId;
        this.valorTotal = valorTotal;
        this.status = status;
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

    @Override
    public String toString() {
        return "ID: " + id + ", Data: " + data + ", ClienteID: " + clienteId + ", Valor Total: " + valorTotal + ", Status: " + status;
    }
}
