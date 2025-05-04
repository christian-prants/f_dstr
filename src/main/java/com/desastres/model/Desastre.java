package com.desastres.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import java.time.LocalDate;

@Entity
@Table(name = "t_desastre")
public class Desastre {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática de ID
    @Column(name = "id_desastre") // Nome da coluna no banco de dados
    private Long id;  // Campo ID

    @Column(name = "nm_tipo") // Nome da coluna no banco de dados
    private String tipo;            // Tipo do desastre
    
    @Column(name = "dt_data") // Nome da coluna no banco de dados
    private LocalDate data;            // Data do desastre
    
    @Column(name = "nr_intensidade") // Nome da coluna no banco de dados
    private Integer intensidade;     // Intensidade do desastre
    
    @Column(name = "hr_duracao") // Nome da coluna no banco de dados
    private Integer duracao;        // Duração do desastre
    
    @Column(name = "t_localizacao_id_localizacao") // Nome da coluna no banco de dados
    private Long localizacaoId;     // ID da localização (FK)
    
    @Column(name = "t_usuarios_id_usuario") // Nome da coluna no banco de dados
    private Long usuarioId;     // ID do usuário (FK)

    // Construtor padrão
    public Desastre() {
    }

    // Construtor com parâmetros
    public Desastre(String tipo, LocalDate data, Integer intensidade, Integer duracao, Long localizacaoId, Long usuarioId) {
        this.tipo = tipo;
        this.data = data;        
        this.intensidade = intensidade;
        this.duracao = duracao;
        this.localizacaoId = localizacaoId;
        this.usuarioId = usuarioId;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Integer getIntensidade() {
        return intensidade;
    }

    public void setIntensidade(Integer intensidade) {
        this.intensidade = intensidade;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public Long getLocalizacaoId() {
        return localizacaoId;
    }

    public void setLocalizacaoId(Long localizacaoId) {
        this.localizacaoId = localizacaoId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
