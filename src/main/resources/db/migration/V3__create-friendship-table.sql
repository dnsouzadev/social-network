-- Criação da tabela friendship para armazenar pares de amigos
CREATE TABLE friendship (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            user1_id BIGINT NOT NULL,
                            user2_id BIGINT NOT NULL,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Chaves estrangeiras para os dois usuários
                            CONSTRAINT fk_friendship_user1 FOREIGN KEY (user1_id) REFERENCES account(id) ON DELETE CASCADE,
                            CONSTRAINT fk_friendship_user2 FOREIGN KEY (user2_id) REFERENCES account(id) ON DELETE CASCADE
);

-- Índice para acelerar a busca de pares de amigos
CREATE INDEX idx_friendship_user1_user2 ON friendship (user1_id, user2_id);
