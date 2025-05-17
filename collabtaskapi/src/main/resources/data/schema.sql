CREATE TABLE account (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password CHAR(60) NOT NULL,
    profile_photo_url VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE
);


CREATE TABLE task (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(30) NOT NULL,
    account_id INT,
    CONSTRAINT fk_task_account FOREIGN KEY (account_id) REFERENCES account(id),
    CONSTRAINT chk_task_status CHECK (status IN ('BACKLOG','TO_DO', 'IN_PROGRESS', 'TESTING', 'DONE'))
);
