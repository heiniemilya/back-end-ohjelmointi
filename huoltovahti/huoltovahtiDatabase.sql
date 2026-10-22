-- Remove tables in the right order based on dependencies
DROP TABLE IF EXISTS task_target;
DROP TABLE IF EXISTS maintenance_task;
DROP TABLE IF EXISTS app_user;
DROP TABLE IF EXISTS maintenance_target;
DROP TABLE IF EXISTS category;


-- category table
CREATE TABLE category (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- maintenance_target table
CREATE TABLE maintenance_target (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    is_common_area BOOLEAN
);

-- app_user table
CREATE TABLE app_user (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    role VARCHAR(100) NOT NULL,
    username VARCHAR(250) NOT NULL,
    password VARCHAR(250) NOT NULL,
    target_id BIGINT REFERENCES maintenance_target(id)
);

-- maintenance_task table
CREATE TABLE maintenance_task (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    task_year INT NOT NULL,
    timeframe VARCHAR(20),
    is_planned BOOLEAN,
    responsibility VARCHAR(10) NOT NULL,
    is_paid_by_company BOOLEAN NOT NULL,
    is_self_performed BOOLEAN,
    contractor VARCHAR(50),
    contractor_business_id VARCHAR(9),
    category_id  BIGINT REFERENCES category(id) NOT NULL,
    created_by_id  BIGINT REFERENCES app_user(id) NOT NULL
);

-- task_target join table
CREATE TABLE task_target (
    task_id BIGINT NOT NULL REFERENCES maintenance_task(id) ON DELETE CASCADE,
    target_id BIGINT NOT NULL REFERENCES maintenance_target(id) ON DELETE CASCADE,
    PRIMARY KEY (task_id, target_id)
);

-- insert data
INSERT INTO category (name) 
VALUES 
('Sisätilat ja pinnat'),
('Ulkopuoli ja julkisivu'),
('Katto ja yläpohja'),
('Perustukset ja alapohja'),
('Sähkötyöt'),
('LVI-työt'),
('Piha- ja aluetyöt');


INSERT INTO maintenance_target (name, is_common_area) 
VALUES 
('A', false),
('B', false),
('C', false),
('D', false),
('E', false),
('Pysäköintialue', true),
('Jätekatos', true),
('Huoltorakennus', true),
('Yhteinen piha', true),
('Raja-aidat', true);

INSERT INTO app_user (name, username, password, role, target_id) 
VALUES 
('Osakas', 'user', '$2a$10$ZxRCz4o.Blm4dc2.xoqYPOe8rWPa10ITUNnx8aL8I9iYxQTFtExz2', 'USER', '2'),
('Hallituksen jäsen', 'admin', '$2a$10$3G014wyNyHftnjJCcUCrhug5S054LZ32is2HRA6nyRVI3nX3AgpyC', 'ADMIN', null);

INSERT INTO maintenance_task (title, description, task_year, timeframe, is_planned, responsibility, is_paid_by_company , 
                is_self_performed, contractor, contractor_business_id, category_id, created_by_id ) 
VALUES 
('Ulkoseinien maalaus', 'Maalattu kaikki A-talon ulkoseinät', 2024, 'kesä-heinäkuu', false, 'Osakas', false, true, null, null, 2, 2),
('Sähkösaneeraus', 'Uusittu B-talon sähköjärjestelmä', 2025, 'helmikuu', false, 'Osakas', false, false, 'Sähkötyöt Oy', '9876543-2', 5, 1),
('Kattoremontti', 'Uusittu kaikkien talojen katot', 2026, 'tammikuu',  false, 'Osakas', true, false, 'Kattoremppa Oy', '1234567-8', 3, 2),
('Jätekatoksen uusinta', null, 2029, null, true, 'Taloyhtiö', true, null, null, null, 7, 2),
('Parkkipaikan asfaltointi', null, 2028, 'kesä', true, 'Taloyhtiö', true, null, null, null, 7, 2),
('Pohjoisen raja-aidan kunnostus', null, 2027, 'kevät', true, 'Taloyhtiö', true, null, null, null, 7, 2);

INSERT INTO task_target (task_id, target_id)
VALUES 
(1, 1),
(2, 2),
(3, 1), (3, 2), (3, 3), (3, 4), (3, 5),
(4, 7),
(5, 6),
(6, 10);

-- Test querys
SELECT * FROM category;
SELECT * FROM app_user;
SELECT * FROM maintenance_target;
SELECT * FROM maintenance_task;
SELECT * FROM task_target;