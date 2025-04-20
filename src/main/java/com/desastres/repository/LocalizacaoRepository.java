package com.desastres.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desastres.model.Localizacao;

public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long> {}

