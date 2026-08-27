package com.projeto.gerenciadordetarefas.domain.tarefa;

import com.projeto.gerenciadordetarefas.domain.Prioridade;
import com.projeto.gerenciadordetarefas.domain.Status;

import java.time.Instant;
import java.time.LocalDateTime;

public record TarefaResponseDTO(
        Long id,
        String tarefa,
        String descricao,
        Prioridade prioridade,
        LocalDateTime dataVencimento,
        Status status,
        Instant criadoAt,
        Instant atualizadoAt


) {}

