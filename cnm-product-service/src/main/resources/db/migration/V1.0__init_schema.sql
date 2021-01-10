-- noinspection SqlDialectInspectionForFile
-- noinspection SqlNoDataSourceInspectionForFile

CREATE SCHEMA IF NOT EXISTS cnm;

CREATE CAST (text AS decimal) WITH INOUT AS IMPLICIT;
CREATE CAST (text AS bigint) WITH INOUT AS IMPLICIT;

CREATE TABLE IF NOT EXISTS cnm.product(
    id              VARCHAR(32) PRIMARY KEY,
    json_content    JSONB        NOT NULL,
    name            VARCHAR(100) GENERATED ALWAYS AS (JSON_CONTENT ->> '$.name') STORED,
    description     VARCHAR(500) GENERATED ALWAYS AS (JSON_CONTENT ->> '$.description') STORED,
    price           DECIMAL(10,2) GENERATED ALWAYS AS (JSON_CONTENT ->> '$.price') STORED,
    created_at      BIGINT GENERATED ALWAYS AS (JSON_CONTENT ->> '$.createdAt') STORED,
    category_id     VARCHAR(32) GENERATED ALWAYS AS (JSON_CONTENT ->> '$.categoryId') STORED
);

CREATE TABLE IF NOT EXISTS cnm.category(
    id              VARCHAR(32) PRIMARY KEY,
    json_content    JSON        NOT NULL,
    name            VARCHAR(100) GENERATED ALWAYS AS (JSON_CONTENT ->> '$.name') STORED,
    description     VARCHAR(500) GENERATED ALWAYS AS (JSON_CONTENT ->> '$.description') STORED,
    created_at      BIGINT GENERATED ALWAYS AS (JSON_CONTENT ->> '$.createdAt') STORED
);