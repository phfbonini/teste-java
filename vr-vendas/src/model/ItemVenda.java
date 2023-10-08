package model;

public class ItemVenda {
    private int id;
    private int produtoId;
    private int quantidade;
    private double preco;
    private double valorTotal;

    public ItemVenda(int id, int produtoId, int quantidade, double preco, double valorTotal) {
        this.id = id;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.preco = preco;
        this.valorTotal = valorTotal;
    }

    public int getId() {
        return id;
    }

    public int getProdutoId() {
        return produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", ProdutoID: " + produtoId + ", Quantidade: " + quantidade + ", Preço: " + preco + ", Valor Total: " + valorTotal;
    }
}
