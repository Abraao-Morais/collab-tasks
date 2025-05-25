INSERT INTO account (name, email, password, profile_photo_url, is_active) VALUES
('Alice', 'alice@example.com', '$2a$10$H9NqSvWUXK9h4mMvYksGxOCWhM/v2SsgAyv4rK0xkyV8hClZ4UCqa', 'https://www.thispersondoesnotexist.com', TRUE),
('Bruno', 'bruno@example.com', '$2a$10$DqOUx4xNPy/m3z2g0wF04OZzqZ70E98pPvY6b6vfx23B1zOU3rPSK', NULL, TRUE),
('Carla', 'carla@example.com', '$2a$10$BR0t6uRdtZZFfNl8fOVl.ez0U8wUirwDHQmSrjGBxe9wBvwNmvO6W', 'https://www.thispersondoesnotexist.com', TRUE),
('Lucas Martins', 'lucas.martins@example.com', '$2a$10$w9r7uOlpzCqX9fV4JkRb3O1Xv6DaZLoB9Up9G0MdWxFH3LTgR7a7m', 'https://www.thispersondoesnotexist.com', TRUE);

INSERT INTO task (title, description, status, account_id) VALUES
('Tarefa 1', 'Descrição da tarefa 1', 'TO_DO', 1),
('Tarefa 2', 'Descrição da tarefa 2', 'IN_PROGRESS', 2),
('Tarefa 3', 'Descrição da tarefa 3', 'DONE', 3);
