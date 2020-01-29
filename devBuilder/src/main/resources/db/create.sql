CREATE TABLE IF NOT EXISTS DEVELOPERS(
    ID IDENTITY PRIMARY KEY AUTO_INCREMENT ,
    ENDING VARCHAR(2) UNIQUE NOT NULL,
    NAME VARCHAR(64),
    SURNAME VARCHAR(64),
    AGE INT,
    SKILLS_COUNT INT,
    BDAY VARCHAR(32),
    RACE VARCHAR(32)
);