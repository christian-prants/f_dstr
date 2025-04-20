package com.desastres.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "t_localizacao") // Nome da tabela correspondente no banco de dados
public class Localizacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática do ID
    @Column(name = "id_localizacao") // Nome da coluna no banco de dados
    private Long id; // Adicionando campo ID

    @Column(name = "nm_nome") // Nome da coluna no banco de dados
    private String nome; // Nome da localização

    @Column(name = "nr_latitude") // Nome da coluna no banco de dados
    private Double latitude; // Latitude

    @Column(name = "nr_longitude") // Nome da coluna no banco de dados
    private Double longitude; // Longitude

    @Column(name = "nm_tipo") // Nome da coluna no banco de dados
    private String tipo; // Tipo da localização

    // Construtor padrão
    public Localizacao() {
    }
    
    // Construtor
    public Localizacao(String nome, Double latitude, Double longitude, String tipo) {
        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tipo = tipo;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
