package com.desastres.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desastres.model.Notificacao;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {}