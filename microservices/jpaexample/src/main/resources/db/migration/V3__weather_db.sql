CREATE TABLE IF NOT EXISTS weather
(
    id            INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
    forecast_date DATE                               NOT NULL,
    latitude      FLOAT(4)                           NOT NULL,
    longitude     FLOAT(4)                           NOT NULL,
    city          VARCHAR                            NOT NULL,
    state         VARCHAR                            NOT NULL,
    temperature   ARRAY[24] NOT NULL
);