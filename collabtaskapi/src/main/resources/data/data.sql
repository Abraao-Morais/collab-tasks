INSERT INTO account (name, email, password, profile_photo_url, is_active) VALUES
('Alice', 'alice@example.com', '$2a$10$GiseHkdvw0Fr7A9KRWbei0mg/PYPhWVjdm42puLf0zR/gIAQrsAGy', 'https://www.thispersondoesnotexist.com', TRUE),
('Bruno', 'bruno@example.com', '$2a$10$GiseHkdvw0Fr7A9KRWbei0mg/PYPhWVjdm42puLf0zR/gIAQrsAGy', NULL, TRUE),
('Carla', 'carla@example.com', '$2a$10$GiseHkdvw0Fr7A9KRWbei0mg/PYPhWVjdm42puLf0zR/gIAQrsAGy', 'https://www.thispersondoesnotexist.com', TRUE),
('Lucas Martins', 'lucas.martins@example.com', '$2a$10$GiseHkdvw0Fr7A9KRWbei0mg/PYPhWVjdm42puLf0zR/gIAQrsAGy', 'https://www.thispersondoesnotexist.com', TRUE);


INSERT INTO task (title, description, status, account_id) VALUES
('Tarefa 1', 'Descrição da tarefa 1', 'TO_DO', 1),
('Tarefa 2', 'Descrição da tarefa 2', 'IN_PROGRESS', 2),
('Tarefa 3', 'Descrição da tarefa 3', 'DONE', 3);
