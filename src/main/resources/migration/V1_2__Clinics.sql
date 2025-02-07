CREATE TABLE IF NOT EXISTS clinic (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    hospital BIGINT NOT NULL,
    name varchar(50) NOT NULL,
    is_deleted tinyint(1) DEFAULT '0',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_hospital_id FOREIGN KEY (hospital) REFERENCES hospital(id)
);
INSERT INTO clinic(hospital,name) VALUES (1, 'General');
INSERT INTO clinic(hospital,name) VALUES (1, 'Stomach');
INSERT INTO clinic(hospital,name) VALUES (2, 'General');