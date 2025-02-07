CREATE TABLE IF NOT EXISTS treatment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    clinic BIGINT NOT NULL,
    name varchar(50) NOT NULL,
    is_deleted tinyint(1) DEFAULT '0',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_clinic_id FOREIGN KEY (clinic) REFERENCES clinic(id)
);
INSERT INTO treatment(clinic, name) VALUES (1, 'Shoulder');
INSERT INTO treatment(clinic, name) VALUES (1, 'Knee');
INSERT INTO treatment(clinic, name) VALUES (2, 'Crohns disease');
INSERT INTO treatment(clinic, name) VALUES (2, 'Irritable Bowel Syndrome');
INSERT INTO treatment(clinic, name) VALUES (2, 'Ulcerative colitis');
INSERT INTO treatment(clinic, name) VALUES (3, 'Gaming addiction');
INSERT INTO treatment(clinic, name) VALUES (3, 'Anxiety');
INSERT INTO treatment(clinic, name) VALUES (3, 'Depression');
