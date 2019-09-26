CREATE SEQUENCE abstract_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE SOME_TABLE
(
  ID            NUMBER(10)                        NOT NULL,
  SOME_STRING   VARCHAR2(64 CHAR)                 NOT NULL,
  STATUS        VARCHAR2(16 CHAR) CHECK (status IN ('STARTED', 'PROCESSING', 'HANDLED'))
);

INSERT INTO SOME_TABLE VALUES (abstract_sequence.nextval, 'some string value', 'STARTED');