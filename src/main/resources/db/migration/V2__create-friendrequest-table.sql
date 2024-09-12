-- Criação da tabela FriendRequest
CREATE TABLE friend_request (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                sender_id BIGINT NOT NULL,
                                receiver_id BIGINT NOT NULL,
                                status ENUM('PENDING', 'ACCEPTED', 'REJECTED') NOT NULL,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Chave estrangeira para o remetente (sender)
                                CONSTRAINT fk_friend_request_sender FOREIGN KEY (sender_id) REFERENCES account(id) ON DELETE CASCADE,

    -- Chave estrangeira para o destinatário (receiver)
                                CONSTRAINT fk_friend_request_receiver FOREIGN KEY (receiver_id) REFERENCES account(id) ON DELETE CASCADE
);

-- Índice para acelerar a busca de solicitações por remetente e destinatário
CREATE INDEX idx_friend_request_sender_receiver ON friend_request (sender_id, receiver_id);
