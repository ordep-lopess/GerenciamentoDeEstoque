package models;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Modelo de entidade Produto / Doação
 */
public class Produto {

    private int id;
    private String nomeDoador;
    private String telefone;
    private String email;
    private String descricao;       // nome/descrição do produto (obrigatório na tabela)
    private String marca;
    private double quantidade;      // em kg ou unidades
    private String animal;          // 'cao', 'gato', 'aves'
    private String tipo;            // 'racao', 'petisco', 'graos'
    private String pacoteFechado;   // 'sim' / 'nao'
    private LocalDate dataDoacao;

    public Produto() { }

    public Produto(int id,
                   String nomeDoador,
                   String telefone,
                   String email,
                   String descricao,
                   String marca,
                   double quantidade,
                   String animal,
                   String tipo,
                   String pacoteFechado,
                   LocalDate dataDoacao) {
        this.id = id;
        this.nomeDoador = nomeDoador;
        this.telefone = telefone;
        this.email = email;
        this.descricao = descricao;
        this.marca = marca;
        setQuantidade(quantidade);
        this.animal = animal;
        this.tipo = tipo;
        this.pacoteFechado = pacoteFechado;
        this.dataDoacao = dataDoacao;
    }

    // Getters / Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNomeDoador() { return nomeDoador; }
    public void setNomeDoador(String nomeDoador) { this.nomeDoador = nomeDoador; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public double getQuantidade() { return quantidade; }
    public void setQuantidade(double quantidade) {
        if (Double.isNaN(quantidade) || Double.isInfinite(quantidade) || quantidade < 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }
        this.quantidade = quantidade;
    }

    public String getAnimal() { return animal; }
    public void setAnimal(String animal) { this.animal = animal; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getPacoteFechado() { return pacoteFechado; }
    public void setPacoteFechado(String pacoteFechado) { this.pacoteFechado = pacoteFechado; }

    public LocalDate getDataDoacao() { return dataDoacao; }
    public void setDataDoacao(LocalDate dataDoacao) { this.dataDoacao = dataDoacao; }

    // Utilitários para ajuste de quantidade em memória
    public boolean adjustQuantidade(double delta) {
        double nova = this.quantidade + delta;
        if (nova < 0) return false;
        this.quantidade = nova;
        return true;
    }

    public void adicionarQuantidade(double kg) {
        if (kg < 0) throw new IllegalArgumentException("Quantidade deve ser positiva");
        adjustQuantidade(kg);
    }

    public boolean retirarQuantidade(double kg) {
        if (kg < 0) throw new IllegalArgumentException("Quantidade deve ser positiva");
        return adjustQuantidade(-kg);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto)) return false;
        Produto produto = (Produto) o;
        return id == produto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nomeDoador='" + nomeDoador + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", descricao='" + descricao + '\'' +
                ", marca='" + marca + '\'' +
                ", quantidade=" + quantidade +
                ", animal='" + animal + '\'' +
                ", tipo='" + tipo + '\'' +
                ", pacoteFechado='" + pacoteFechado + '\'' +
                ", dataDoacao=" + dataDoacao +
                '}';
    }
}
