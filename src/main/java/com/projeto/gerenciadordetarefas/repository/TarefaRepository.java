package com.projeto.gerenciadordetarefas.repository;

import com.projeto.gerenciadordetarefas.domain.tarefa.Tarefa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    Optional<Tarefa> findById(Long id);

    Page<Tarefa> findByUsuarioId(Long usuarioId, Pageable pageable);

}
