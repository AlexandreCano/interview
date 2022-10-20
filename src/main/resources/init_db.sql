CREATE DATABASE testdb;
CREATE SCHEMA IF NOT EXISTS test_schema;

CREATE TABLE IF NOT EXISTS test_schema.stats (
    id SERIAL PRIMARY KEY,
    customer VARCHAR ( 255 ) NOT NULL,
    content VARCHAR ( 255 ) NOT NULL,
    cdn BIGINT NOT NULL,
    p2p BIGINT NOT NULL,
    TIME TIMESTAMP NOT NULL);

CREATE USER test_user WITH PASSWORD 'password';

GRANT CONNECT ON DATABASE testdb TO test_user;
GRANT USAGE ON SCHEMA test_schema TO test_user;
GRANT SELECT, INSERT, UPDATE ON test_schema.stats TO test_user;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA test_schema to test_user;