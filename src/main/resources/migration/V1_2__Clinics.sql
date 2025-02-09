CREATE TABLE IF NOT EXISTS clinic (
    id BIGINT PRIMARY KEY,
    hospital BIGINT NOT NULL,
    name varchar(50) NOT NULL,
    is_deleted tinyint(1) DEFAULT '0',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_hospital_id FOREIGN KEY (hospital) REFERENCES hospital(id)
);
INSERT INTO clinic(id,hospital,name) VALUES (1,1, 'General');
INSERT INTO clinic(id,hospital,name) VALUES (2,1, 'Stomach');
INSERT INTO clinic(id,hospital,name) VALUES (3,2, 'General');