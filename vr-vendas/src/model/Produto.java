package model;

public class Produto {
    private int id;
    private String descricao;
    private double preco;
    private int quantidade;

    public Produto(int id, String descricao, double preco, int quantidade) {
        this.id = id;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Descrição: " + descricao + ", Preço: " + preco + ", Quantidade: " + quantidade;
    }
}
