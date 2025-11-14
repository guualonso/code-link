package com.codelink.repository;

import com.codelink.model.Comunidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComunidadeRepository extends JpaRepository<Comunidade, Long> {
    // métodos custom se precisar
}
