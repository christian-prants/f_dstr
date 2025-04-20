package com.desastres.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.desastres.model.Desastre;

public interface DesastreRepository extends JpaRepository<Desastre, Long> {}
