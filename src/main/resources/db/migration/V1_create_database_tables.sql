CREATE TABLE t_alerta (
    id_alerta              BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    txt_mensagem           VARCHAR2(250) NOT NULL,
    dt_data_emissao        DATE NOT NULL,
    nm_tipo_risco          VARCHAR2(20) NOT NULL,
    t_desastre_id_desastre NUMBER NOT NULL
);

CREATE TABLE t_desastre (
    id_desastre                BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    nm_tipo                    VARCHAR2(20) NOT NULL,
    dt_data                    DATE NOT NULL,
    nr_intensidade             NUMBER NOT NULL,
    hr_duracao                 NUMBER NOT NULL,
    t_usuarios_id_usuario      NUMBER NOT NULL,
    t_localizacao_id_localizacao NUMBER NOT NULL
);

CREATE TABLE t_localizacao (
    id_localizacao BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    nm_nome        VARCHAR2(20) NOT NULL,
    nr_latitude    NUMBER NOT NULL,
    nr_longitude   NUMBER NOT NULL,
    nm_tipo        VARCHAR2(20) NOT NULL
);

CREATE TABLE t_notificacao (
    id_notificacao     BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    dt_data            DATE NOT NULL,
    t_alerta_id_alerta NUMBER NOT NULL
);

CREATE TABLE t_usuarios (
    id_usuario  BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    nm_nome     VARCHAR2(80) NOT NULL,
    nm_email    VARCHAR2(80) NOT NULL,
    nm_senha       VARCHAR(255) NOT NULL,
    nr_telefone NUMBER NOT NULL
);

ALTER TABLE t_alerta
    ADD CONSTRAINT t_alerta_t_desastre_fk FOREIGN KEY (t_desastre_id_desastre)
        REFERENCES t_desastre (id_desastre);

ALTER TABLE t_desastre
    ADD CONSTRAINT t_desastre_t_localizacao_fk FOREIGN KEY (t_localizacao_id_localizacao)
        REFERENCES t_localizacao (id_localizacao);

ALTER TABLE t_desastre
    ADD CONSTRAINT t_desastre_t_usuarios_fk FOREIGN KEY (t_usuarios_id_usuario)
        REFERENCES t_usuarios (id_usuario);

ALTER TABLE t_notificacao
    ADD CONSTRAINT t_notificacao_t_alerta_fk FOREIGN KEY (t_alerta_id_alerta)
        REFERENCES t_alerta (id_alerta);
