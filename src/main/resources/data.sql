INSERT INTO users (id, user_name, name, email, password, type, created_at)
VALUES
    (1, 'admin', 'Administrador Salud CR', 'admin@saludcr.com',
     '$2a$12$8K/CSRPp5/Q1kdmktEWxp.F510eP4Bk0ybLIZn4ySKXonlf8.wyCi',
     'ADMIN', NOW()),

    (2, 'natalia', 'Natalia Martínez', 'natalia@saludcr.com',
     '$2a$12$8K/CSRPp5/Q1kdmktEWxp.F510eP4Bk0ybLIZn4ySKXonlf8.wyCi',
     'REGULAR', NOW()),
    (3, 'kevin', 'Kevin Hidalgo', 'kevin@saludcr.com',
     '$2a$12$8K/CSRPp5/Q1kdmktEWxp.F510eP4Bk0ybLIZn4ySKXonlf8.wyCi',
     'REGULAR', NOW()),
    (4, 'pedro', 'Pedro Perez', 'pedro@saludcr.com',
     '$2a$12$8K/CSRPp5/Q1kdmktEWxp.F510eP4Bk0ybLIZn4ySKXonlf8.wyCi',
     'REGULAR', NOW());

INSERT INTO appointments (user_id, appointment_date, reason, status, created_at)
VALUES
    (2, '2026-07-01 09:00:00', 'Consulta general', 'SCHEDULED', NOW()),
    (2, '2026-07-03 14:30:00', 'Revisión de resultados de laboratorio', 'SCHEDULED', NOW()),
    (2, '2026-06-20 10:00:00', 'Control médico mensual', 'COMPLETED', NOW()),
    (2, '2026-06-10 08:30:00', 'Seguimiento por dolor de cabeza', 'COMPLETED', NOW()),
    (2, '2026-06-25 15:00:00', 'Consulta cancelada por disponibilidad', 'CANCELLED', NOW()),
    (2, '2026-06-28 11:00:00', 'Reprogramación de cita médica', 'CANCELLED', NOW()),
    (3, '2026-07-01 10:00:00', 'Consulta general', 'SCHEDULED', NOW()),
    (3, '2026-07-03 15:30:00', 'Revisión de resultados de laboratorio', 'SCHEDULED', NOW()),
    (3, '2026-06-20 11:00:00', 'Control médico mensual', 'COMPLETED', NOW()),
    (3, '2026-06-10 09:30:00', 'Seguimiento por dolor de cabeza', 'COMPLETED', NOW()),
    (3, '2026-06-25 16:00:00', 'Consulta cancelada por disponibilidad', 'CANCELLED', NOW()),
    (3, '2026-06-28 12:00:00', 'Reprogramación de cita médica', 'CANCELLED', NOW()),
    (4, '2026-07-01 12:00:00', 'Consulta general', 'SCHEDULED', NOW()),
    (4, '2026-07-03 16:30:00', 'Revisión de resultados de laboratorio', 'SCHEDULED', NOW()),
    (4, '2026-06-20 12:00:00', 'Control médico mensual', 'COMPLETED', NOW()),
    (4, '2026-06-10 10:30:00', 'Seguimiento por dolor de cabeza', 'COMPLETED', NOW()),
    (4, '2026-06-25 17:00:00', 'Consulta cancelada por disponibilidad', 'CANCELLED', NOW()),
    (4, '2026-06-28 13:00:00', 'Reprogramación de cita médica', 'CANCELLED', NOW());