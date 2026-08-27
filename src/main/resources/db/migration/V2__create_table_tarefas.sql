CREATE TABLE tarefa (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao TEXT,
    status VARCHAR(20) NOT NULL,
    prioridade VARCHAR(20) NOT NULL,
    data_vencimento DATE,
    criado_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    completo_at TIMESTAMP,
    usuario_id BIGINT NOT NULL,

        CONSTRAINT fk_tarefa_usuario
            FOREIGN KEY (usuario_id)
            REFERENCES usuario(id)
);