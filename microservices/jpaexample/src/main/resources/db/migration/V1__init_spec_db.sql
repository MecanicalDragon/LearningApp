CREATE SEQUENCE spec_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE SPECIFICATION_TABLE
(
    ID           BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    BIRTH_DATE   DATE                   NOT NULL,
    FIRST_NAME   VARCHAR                NOT NULL,
    LAST_NAME    VARCHAR                NOT NULL,
    PHONE_NUMBER VARCHAR                NOT NULL
);

INSERT INTO SPECIFICATION_TABLE
VALUES (spec_sequence.nextval, '1985-01-20', 'Viktor', 'Borne', '931214');
INSERT INTO SPECIFICATION_TABLE
VALUES (spec_sequence.nextval, '1986-02-21', 'Meth', 'Cox', '123456');
INSERT INTO SPECIFICATION_TABLE
VALUES (spec_sequence.nextval, '1988-03-15', 'Bun', 'Eater', '654987');
INSERT INTO SPECIFICATION_TABLE
VALUES (spec_sequence.nextval, '1990-04-17', 'Snake', 'Sol', '698547');
INSERT INTO SPECIFICATION_TABLE
VALUES (spec_sequence.nextval, '1991-05-03', 'God', 'Damn', '444666');
INSERT INTO SPECIFICATION_TABLE
VALUES (spec_sequence.nextval, '1994-06-08', 'Cratos', 'Bricks', '131313');
INSERT INTO SPECIFICATION_TABLE
VALUES (spec_sequence.nextval, '1997-07-27', 'Stan', 'Lee', '911546');
INSERT INTO SPECIFICATION_TABLE
VALUES (spec_sequence.nextval, '2001-08-25', 'Bin', 'Trash', '555777');
INSERT INTO SPECIFICATION_TABLE
VALUES (spec_sequence.nextval, '2003-09-02', 'Tony', 'Stark', '141555');
INSERT INTO SPECIFICATION_TABLE
VALUES (spec_sequence.nextval, '1990-10-14', 'Kane', 'Wane', '424889');
INSERT INTO SPECIFICATION_TABLE
VALUES (spec_sequence.nextval, '1988-11-16', 'Donny', 'Trigger', '501500');
INSERT INTO SPECIFICATION_TABLE
VALUES (spec_sequence.nextval, '1978-12-01', 'Bob', 'Marley', '667810');