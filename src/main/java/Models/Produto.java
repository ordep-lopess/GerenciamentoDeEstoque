/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author pedroH, bianca
 */

public class Produto {

    private int id;
    private String nomeDoador;
    private String telefone;
    private String email;
    private String descricao;
    private String marca;
    private double quantidade; // em kg
    private String animal;
    private String tipo;
    private String pacoteFechado;
    private LocalDate dataDoacao;

    // campo adicional mínimo solicitado (não altera lógica existente)
    private String produtoDescricao;

    public Produto() { }

    public Produto(int id, String nomeDoador, String telefone, String email,
                   String descricao, String marca, double quantidade,
                   String animal, String tipo, String pacoteFechado, LocalDate dataDoacao) {
        this.id = id;
        this.nomeDoador = nomeDoador;
        this.telefone = telefone;
        this.email = email;
        this.descricao = descricao;
        this.marca = marca;
        this.quantidade = quantidade;
        this.animal = animal;
        this.tipo = tipo;
        this.pacoteFechado = pacoteFechado;
        this.dataDoacao = dataDoacao;
    }

    // getters / setters
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
        if (Double.isNaN(quantidade) || Double.isInfinite(quantidade)) {
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

    // novo getter/setter mínimo para produtoDescricao
    public String getProdutoDescricao() { return produtoDescricao; }
    public void setProdutoDescricao(String produtoDescricao) { this.produtoDescricao = produtoDescricao; }

    // utilitários para ajuste de quantidade em memória
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
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", marca='" + marca + '\'' +
                ", quantidade=" + quantidade +
                '}';
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
}
