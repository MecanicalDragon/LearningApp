-- CREATE SEQUENCE user_sequence;

CREATE TABLE USER
(
--     ID      BIGINT default user_sequence.nextval primary key NOT NULL,
    ID   BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    NAME CHAR NOT NULL
);

INSERT INTO USER (ID, NAME)
VALUES (1, 'Vasiliy');