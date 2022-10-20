ALTER TABLE IF EXISTS test_schema.stats
    ADD COLUMN sessions integer NOT NULL DEFAULT 1;