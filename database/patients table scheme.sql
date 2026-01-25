CREATE TABLE IF NOT EXISTS patients(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    gender ENUM('FEMALE','MALE') NOT NULL,
    birth_date DATE NOT NULL,
    middle_name VARCHAR(255),
    last_name VARCHAR(255) NOT NULL,
);