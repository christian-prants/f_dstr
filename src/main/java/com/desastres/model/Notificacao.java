package com.desastres.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "t_notificacao") // Nome da tabela correspondente no banco de dados
public class Notificacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática de ID
    @Column(name = "id_notificacao") // Nome da coluna no banco de dados
    private Long id; // Adicionando campo ID

    @Column(name = "txt_mensagem") // Nome da coluna no banco de dados
    private String mensagem; // Mensagem da notificação
    
    @Column(name = "t_alerta_id_alerta") // Nome da coluna no banco de dados
    private Long t_alerta_id_alerta; // ID do usuário

    // Construtor
    public Notificacao(String mensagem, Long t_alerta_id_alerta) {
        this.mensagem = mensagem;
        this.t_alerta_id_alerta = t_alerta_id_alerta;
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

    public Long getAlertaId() {
        return t_alerta_id_alerta;
    }

    public void setAlertaId(Long t_alerta_id_alerta) {
        this.t_alerta_id_alerta = t_alerta_id_alerta;
    }
}
