--liquibase formatted sql

--changeset dmiit3iy:1
CREATE TABLE IF NOT EXISTS users
(
    id         SERIAL PRIMARY KEY,
    email      varchar(255) UNIQUE,
    first_name varchar(255),
    last_name  varchar(255),
    chat_id    varchar(255),
    telegram   varchar(255)
);
--rollback DROP TABLE users;

--changeset dmiit3iy:2
CREATE TABLE IF NOT EXISTS reminder
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(255)  NOT NULL,
    description VARCHAR(4096) NOT NULL,
    remind      TIMESTAMP     NOT NULL,
    user_id     BIGINT        NOT NULL,
    is_send      BOOLEAN  default false  not null,
    FOREIGN KEY (user_id) REFERENCES users (id)
);
--rollback DROP TABLE reminder;