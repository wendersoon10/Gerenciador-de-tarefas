package com.projeto.gerenciadordetarefas.domain.usuario;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.jspecify.annotations.Nullable;

import java.time.Instant;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(unique = true)
    private String email;
    private String senha;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant criadoAt;
    @CreationTimestamp
    @Column(nullable = false)
    private Instant atualizadoAt;



}
