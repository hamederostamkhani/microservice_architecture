CREATE TABLE IF NOT EXISTS store.sellar (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(200) NOT NULL,
    last_name VARCHAR(200) NOT NULL,
    email VARCHAR(300) UNIQUE NOT NULL,
    image TEXT,
    valid_from TIMESTAMP NOT NULL,
    valid_to TIMESTAMP
);