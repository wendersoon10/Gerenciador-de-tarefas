package com.projeto.gerenciadordetarefas.domain.usuario;

import java.time.Instant;

public record UsuarioResponseDto(Long id, String nome, String email, Instant criadoAt, Instant atualizadoAt) {
}
