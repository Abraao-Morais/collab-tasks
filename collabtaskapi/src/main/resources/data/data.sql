INSERT INTO account (name, email, password, profile_photo_url, is_active) VALUES
('Alice', 'alice@example.com', '$2a$10$CwjIio6goc3EJ8JGmLeOJuHFz6iaextIYbrrJUhKS5Kd5Rp8CKm8i', 'https://www.thispersondoesnotexist.com', TRUE),
('Bruno', 'bruno@example.com', '$2a$10$CwjIio6goc3EJ8JGmLeOJuHFz6iaextIYbrrJUhKS5Kd5Rp8CKm8i', NULL, TRUE),
('Carla', 'carla@example.com', '$2a$10$CwjIio6goc3EJ8JGmLeOJuHFz6iaextIYbrrJUhKS5Kd5Rp8CKm8i', 'https://www.thispersondoesnotexist.com', TRUE),
('Lucas Martins', 'lucas.martins@example.com', '$2a$10$CwjIio6goc3EJ8JGmLeOJuHFz6iaextIYbrrJUhKS5Kd5Rp8CKm8i', 'https://www.thispersondoesnotexist.com', TRUE);


INSERT INTO task (title, description, status, account_id) VALUES
('Tarefa 1', 'Descrição da tarefa 1', 'TO_DO', 1),
('Tarefa 2', 'Descrição da tarefa 2', 'IN_PROGRESS', 2),
('Tarefa 3', 'Descrição da tarefa 3', 'DONE', 3);
