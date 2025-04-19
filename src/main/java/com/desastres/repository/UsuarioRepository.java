package com.desastres.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desastres.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Usuario findByEmail(String email);
}
