package com.projeto.gerenciadordetarefas.domain.usuario;

import com.projeto.gerenciadordetarefas.domain.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
    @Enumerated(EnumType.STRING)
    private Role role;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant criadoAt;
    @CreationTimestamp
    @Column(nullable = false)
    private Instant atualizadoAt;



}
