CREATE TABLE IF NOT EXISTS hospital (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name varchar(50) NOT NULL,
    is_deleted tinyint(1) DEFAULT '0',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
INSERT INTO hospital(name) VALUES ('KMC Hospitals');
INSERT INTO hospital(name) VALUES ('Apollo Hospitals');
