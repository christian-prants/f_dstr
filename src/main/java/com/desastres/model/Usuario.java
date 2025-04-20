package com.desastres.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "t_usuarios") // Nome da tabela correspondente no banco de dados
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática do ID
    @Column(name = "id_usuario") // Nome da coluna no banco de dados
    private Long id; // Adicionando campo ID

    @Column(name = "nm_nome") // Nome da coluna no banco de dados
    private String nome; // Nome do usuário

    @Column(name = "nm_email") // Nome da coluna no banco de dados
    private String email; // Email do usuário

    @Column(name = "nm_senha") // Nome da coluna no banco de dados
    private String senha; // Senha do usuário
    
    @Column(name = "nr_telefone") // Nome da coluna no banco de dados
    private String telefone; // Telefone do usuário

    // Construtor padrão
    public Usuario() {
    }

    // Construtor com parâmetros
    public Usuario(String nome, String email, String senha, String telefone) {
        this.nome = nome;
        this.email = email;        
        this.senha = senha;
        this.telefone = telefone;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
