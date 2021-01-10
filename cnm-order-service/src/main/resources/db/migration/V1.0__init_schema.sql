-- noinspection SqlDialectInspectionForFile
-- noinspection SqlNoDataSourceInspectionForFile

CREATE SCHEMA IF NOT EXISTS cnm;

CREATE CAST (text AS decimal) WITH INOUT AS IMPLICIT;
CREATE CAST (text AS bigint) WITH INOUT AS IMPLICIT;

CREATE TABLE IF NOT EXISTS cnm.order(
    id              VARCHAR(32) PRIMARY KEY,
    created_at      BIGINT NOT NULL,
    updated_at      BIGINT NOT NULL,
    version         BIGINT NOT NULL,
    total_price     DECIMAL(10,2) NOT NULL,
    state           VARCHAR(32) NOT NULL,
    province        VARCHAR(255) NOT NULL,
    city            VARCHAR(255) NOT NULL,
    detail          VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS cnm.order_items(
    order_id        VARCHAR(32)     NOT NULL,
    product_id      VARCHAR(32)     NOT NULL,
    count           INTEGER         NOT NULL,
    unit_price      DECIMAL(10,2)   NOT NULL,
    FOREIGN KEY (order_id) REFERENCES cnm.order (id)
);
-- INSERT INTO cnm.order(id, json_content) VALUES('c7cac1d756174334b5ee3a35a33b6aea', '"createdAt":1610084652601,"updatedAt":1610084652601,"items":[],"totalPrice":0,"state":"APPROVAL_PENDING","address":{"province":"广东省","city":"广州市","detail":"番禺区华创产业园二期"}')