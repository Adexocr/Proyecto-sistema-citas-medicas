-- Salud CR Schema para la Base de Datos

CREATE TABLE IF NOT EXISTS users (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name   VARCHAR(50)  NOT NULL UNIQUE,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(100) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    type        ENUM('ADMIN', 'REGULAR') NOT NULL DEFAULT 'REGULAR',
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP

);

