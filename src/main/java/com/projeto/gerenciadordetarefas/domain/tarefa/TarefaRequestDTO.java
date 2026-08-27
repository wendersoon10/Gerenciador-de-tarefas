package com.projeto.gerenciadordetarefas.domain.tarefa;

import com.projeto.gerenciadordetarefas.domain.Prioridade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TarefaRequestDTO(
        @NotBlank(message = "nome da tarefa é obrigatória")
        String tarefa,
        @NotBlank(message = "Descrição da tarefa é obrigatória")
        String descricao,
        @NotNull
        Prioridade prioridade,
        LocalDateTime dataVencimento,
        Long usuarioId
) {
}