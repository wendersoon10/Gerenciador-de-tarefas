package com.projeto.gerenciadordetarefas.domain.tarefa;

import com.projeto.gerenciadordetarefas.domain.Prioridade;
import com.projeto.gerenciadordetarefas.domain.Status;
import com.projeto.gerenciadordetarefas.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "tarefa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Tarefa {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    private LocalDateTime dataVencimento;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant criadoAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant atualizadoAt;
    private Instant completoAt;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
