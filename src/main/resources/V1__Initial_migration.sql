CREATE TABLE tickets (
    id SERIAL PRIMARY KEY,
    number INT NOT NULL,
    timestamp TIMESTAMP,
    position INT NOT NULL
);
