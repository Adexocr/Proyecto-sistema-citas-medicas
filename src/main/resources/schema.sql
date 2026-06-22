DROP TABLE IF EXISTS appointments;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       user_name VARCHAR(100) NOT NULL UNIQUE,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(150) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       type VARCHAR(20) NOT NULL,
                       created_at DATETIME NOT NULL
);

CREATE TABLE appointments (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              user_id BIGINT NOT NULL,
                              appointment_date DATETIME NOT NULL,
                              reason VARCHAR(500),
                              status VARCHAR(20) NOT NULL,
                              created_at DATETIME NOT NULL,

                              CONSTRAINT fk_appointment_user
                                  FOREIGN KEY (user_id)
                                      REFERENCES users(id)
);