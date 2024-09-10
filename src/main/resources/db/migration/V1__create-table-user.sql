CREATE TABLE account (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         username VARCHAR(255) NOT NULL,
                         password VARCHAR(255) NOT NULL,
                         type_account VARCHAR(20) NOT NULL
);

INSERT INTO account (username, password, type_account)
VALUES ('admin', 'admin', 'PUBLIC');