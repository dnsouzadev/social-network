CREATE TABLE account (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         username VARCHAR(255) NOT NULL UNIQUE,
                         password VARCHAR(255) NOT NULL,
                         type_account VARCHAR(20) NOT NULL,
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- INSERT INTO account (username, password, type_account, createdAt)
-- VALUES ('admin', 'admin', 'PUBLIC', CURRENT_TIMESTAMP);