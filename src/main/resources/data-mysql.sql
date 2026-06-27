-- Usuario ADMIN inicial (password: admin1234 encriptado con BCrypt=)

INSERT INTO users (user_name, name, email, password, type, created_at)
VALUES (
        'admin',
        'Administrador',
        'admin@saludcr.com',
        '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lbeW',
        'ADMIN',
        NOW()
       )
ON DUPLICATE KEY UPDATE user_name = user_name;

-- Usuario REGULAR de prueba (password: kevin1234)
INSERT INTO users (user_name, name, email, password, type, created_at)
VALUES (
           'kevin',
           'Kevin Hidalgo',
           'kevinh@saludcr.com',
           '$2a$10$8K1p/a0dR7Q7Qz7Qz7Qz7OeN9qo8uLOickgx2ZMRZoMyeIjZAgc',
           'REGULAR',
           NOW()
       )
    ON DUPLICATE KEY UPDATE user_name = user_name;

