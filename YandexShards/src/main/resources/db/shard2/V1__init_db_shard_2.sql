CREATE SEQUENCE IF NOT EXISTS PAYMENT_SEQUENCE INCREMENT BY 3 START WITH 2;

CREATE TABLE IF NOT EXISTS PAYMENT
(
    ID BIGINT PRIMARY KEY NOT NULL DEFAULT NEXTVAL('PAYMENT_SEQUENCE'),
    AMOUNT INT DEFAULT 0 NOT NULL CHECK ( AMOUNT > 0 ),
    SENDER BIGINT NOT NULL CHECK ( SENDER > 0 ),
    RECIPIENT BIGINT NOT NULL CHECK ( RECIPIENT > 0 )
);

CREATE INDEX IF NOT EXISTS PAYMENT_SENDER ON PAYMENT(SENDER);