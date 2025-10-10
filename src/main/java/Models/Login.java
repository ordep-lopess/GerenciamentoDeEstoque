/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author pedroH, bianca
 */
public class Login {
    
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String rua;
    private String num;
    private String cep;

    public Login() {
    }

    public Login(int id, String nome, String email, String senha, String rua, String num, String cep) {
        this.id    = id;
        this.nome  = nome;
        this.email = email;
        this.senha = senha;
        this.rua   = rua;
        this.num   = num;
        this.cep   = cep;
    }

    public Login(int id, String nome, String email, String senha) {
        this.id    = id;
        this.nome  = nome;
        this.email = email;
        this.senha = senha;
    }

    public Login(int id, String email, String senha) {
        this.id    = id;
        this.email = email;
        this.senha = senha;
    }

    // id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // senha
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    // rua
    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    // num
    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    // cep
    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
