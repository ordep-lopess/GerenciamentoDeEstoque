package models;

public class ProdutoObservacao {
    private int id; // id da observação (se usar autoincrement)
    private int idProduto; // id_doacao
    private String observacao;

    public ProdutoObservacao() {}

    public ProdutoObservacao(int id, int idProduto, String observacao) {
        this.id = id;
        this.idProduto = idProduto;
        this.observacao = observacao;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdProduto() { return idProduto; }
    public void setIdProduto(int idProduto) { this.idProduto = idProduto; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}
