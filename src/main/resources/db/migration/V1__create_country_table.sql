CREATE TABLE country (
    country_code CHAR(2) PRIMARY KEY ,
    name TEXT NOT NULL UNIQUE,
    population INT NOT NULL CHECK (population >= 0),
    eu_member BOOLEAN NOT NULL
);
