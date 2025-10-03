package models;

import java.io.Serializable;
import java.util.Objects;

public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String contato;
    private String descricao;
    private String marca;
    private double quantidade;
    private String animal;

    public Produto() { }

    public Produto(int id,
                   String nome,
                   String contato,
                   String descricao,
                   String marca,
                   double quantidade,
                   String animal) {
        this.id         = id;
        this.nome       = nome;
        this.contato    = contato;
        this.descricao  = descricao;
        this.marca      = marca;
        this.quantidade = quantidade;
        this.animal     = animal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    @Override
    public String toString() {
        return "Produto{" +
               "id=" + id +
               ", nome='" + nome + '\'' +
               ", contato='" + contato + '\'' +
               ", descricao='" + descricao + '\'' +
               ", marca='" + marca + '\'' +
               ", quantidade=" + quantidade +
               ", animal='" + animal + '\'' +
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
