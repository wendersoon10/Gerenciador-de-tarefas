package com.projeto.gerenciadordetarefas.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequestDto(
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        @NotBlank(message = "Email é obrigatorio")
        String email,
        @NotNull(message = "Senha é obrigatória")
        String senha) {

}
