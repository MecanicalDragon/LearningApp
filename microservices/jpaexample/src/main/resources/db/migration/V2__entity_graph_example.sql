CREATE SEQUENCE eg_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE post_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE comment_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE USER
(
    ID    BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    NAME  VARCHAR                           NOT NULL,
    EMAIL VARCHAR                           NOT NULL
);

INSERT INTO USER
VALUES (eg_sequence.nextval, 'Viktor', 'Borne@mail.com');
INSERT INTO USER
VALUES (eg_sequence.nextval, 'Meth', 'Cox@mail.com');
INSERT INTO USER
VALUES (eg_sequence.nextval, 'Bun', 'Eater@mail.com');
INSERT INTO USER
VALUES (eg_sequence.nextval, 'Snake', 'Sol@mail.com');
INSERT INTO USER
VALUES (eg_sequence.nextval, 'God', 'Damn@mail.com');
INSERT INTO USER
VALUES (eg_sequence.nextval, 'Cratos', 'Bricks@mail.com');
INSERT INTO USER
VALUES (eg_sequence.nextval, 'Stan', 'Lee@mail.com');
INSERT INTO USER
VALUES (eg_sequence.nextval, 'Bin', 'Trash@mail.com');
INSERT INTO USER
VALUES (eg_sequence.nextval, 'Tony', 'Stark@mail.com');
INSERT INTO USER
VALUES (eg_sequence.nextval, 'Kane', 'Wane@mail.com');
INSERT INTO USER
VALUES (eg_sequence.nextval, 'Donny', 'Trigger@mail.com');
INSERT INTO USER
VALUES (eg_sequence.nextval, 'Bob', 'Marley@mail.com');

CREATE TABLE POST
(
    ID      BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    SUBJECT VARCHAR                           NOT NULL,
    USER_ID BIGINT                            NOT NULL
        CONSTRAINT OWNER REFERENCES USER (ID)
);

INSERT INTO POST
VALUES (post_sequence.nextval, 'IT', eg_sequence.currval);
INSERT INTO POST
VALUES (post_sequence.nextval, 'Politics', eg_sequence.currval);
INSERT INTO POST
VALUES (post_sequence.nextval, 'Memes', eg_sequence.currval);

CREATE TABLE COMMENT
(
    ID      BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    REPLY   VARCHAR                           NOT NULL,
    USER_ID BIGINT                            NOT NULL
        CONSTRAINT COMMENT_OWNER REFERENCES USER (ID),
    POST_ID BIGINT                            NOT NULL
        CONSTRAINT COMMENTED_POST REFERENCES POST (ID)
);

INSERT INTO COMMENT
VALUES (comment_sequence.nextval, 'good', SELECT ID FROM USER WHERE NAME = 'Stan', post_sequence.currval);
INSERT INTO COMMENT
VALUES (comment_sequence.nextval, 'bad', SELECT ID FROM USER WHERE NAME = 'Bob', post_sequence.currval);
INSERT INTO COMMENT
VALUES (comment_sequence.nextval, 'average', SELECT ID FROM USER WHERE NAME = 'God', post_sequence.currval);

INSERT INTO COMMENT
VALUES (comment_sequence.nextval, 'shit', SELECT ID FROM USER WHERE NAME = 'Viktor', 2);
INSERT INTO COMMENT
VALUES (comment_sequence.nextval, 'sucks', SELECT ID FROM USER WHERE NAME = 'Bun', 2);
INSERT INTO COMMENT
VALUES (comment_sequence.nextval, 'crap', SELECT ID FROM USER WHERE NAME = 'Tony', 2);

CREATE TABLE ATTACHMENT
(
    POST_ID     BIGINT  NOT NULL,
    PAYLOAD     VARCHAR NOT NULL,
    DESCRIPTION VARCHAR NOT NULL
);

INSERT INTO ATTACHMENT
VALUES (post_sequence.currval, 'payload1', 'description1');
INSERT INTO ATTACHMENT
VALUES (post_sequence.currval, 'payload2', 'description2');
INSERT INTO ATTACHMENT
VALUES (post_sequence.currval, 'payload2', 'description2');

INSERT INTO ATTACHMENT
VALUES (2, 'photo1', 'politic1');
INSERT INTO ATTACHMENT
VALUES (2, 'photo2', 'politic2');

CREATE TABLE SUBSCRIPTION
(
    ID    BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    TOPIC VARCHAR                           NOT NULL
);

INSERT INTO SUBSCRIPTION
VALUES (1, 'Fiction');
INSERT INTO SUBSCRIPTION
VALUES (2, 'Science');
INSERT INTO SUBSCRIPTION
VALUES (3, 'Fun');
INSERT INTO SUBSCRIPTION
VALUES (4, 'Pron');

CREATE TABLE USER_SUBSCRIPTION
(
    USER_ID BIGINT NOT NULL,
    SUB_ID  BIGINT NOT NULL
);

INSERT INTO USER_SUBSCRIPTION
VALUES (1, 1);
INSERT INTO USER_SUBSCRIPTION
VALUES (1, 2);
INSERT INTO USER_SUBSCRIPTION
VALUES (1, 3);
INSERT INTO USER_SUBSCRIPTION
VALUES (2, 1);
INSERT INTO USER_SUBSCRIPTION
VALUES (2, 4);
INSERT INTO USER_SUBSCRIPTION
VALUES (3, 2);
INSERT INTO USER_SUBSCRIPTION
VALUES (3, 4);
INSERT INTO USER_SUBSCRIPTION
VALUES (4, 1);
INSERT INTO USER_SUBSCRIPTION
VALUES (4, 4);
