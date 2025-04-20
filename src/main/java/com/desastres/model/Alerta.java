package com.desastres.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import java.util.Date;

@Entity
@Table(name = "t_alerta") // Nome da tabela correspondente no banco de dados
public class Alerta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática do ID
    @Column(name = "id_alerta") // Nome da coluna no banco de dados
    private Long id; // Campo ID

    @Column(name = "txt_mensagem") // Nome da coluna no banco de dados
    private String mensagem;

    @Column(name = "dt_data_emissao") // Nome da coluna no banco de dados
    private Date dataEmissao; // Data de emissão

    @Column(name = "nm_tipo_risco") // Nome da coluna no banco de dados
    private String tipoRisco; // Tipo de risco

    @Column(name = "t_desastre_id_desastre") // Nome da coluna no banco de dados
    private Long desastreId; // Referência ao ID do desastre

    // Construtor
    public Alerta(String mensagem, Date dataEmissao, String tipoRisco, Long desastreId) {
        this.mensagem = mensagem;
        this.dataEmissao = dataEmissao;
        this.tipoRisco = tipoRisco;
        this.desastreId = desastreId;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getTipoRisco() {
        return tipoRisco;
    }

    public void setTipoRisco(String tipoRisco) {
        this.tipoRisco = tipoRisco;
    }

    public Long getDesastreId() {
        return desastreId;
    }

    public void setDesastreId(Long desastreId) {
        this.desastreId = desastreId;
    }
}
