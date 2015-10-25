DROP TABLE job IF EXISTS;

CREATE TABLE job (
  key       INTEGER IDENTITY PRIMARY KEY,
  code      VARCHAR(30),
  name      VARCHAR(30),
  version   INTEGER
);
