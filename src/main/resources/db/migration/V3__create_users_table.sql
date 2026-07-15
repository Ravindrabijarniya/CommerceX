CREATE TABLE users (

    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    first_name VARCHAR(50) NOT NULL,

    last_name VARCHAR(50) NOT NULL,

    email VARCHAR(255) NOT NULL,

    password VARCHAR(255) NOT NULL,

    enabled BOOLEAN NOT NULL,

    account_locked BOOLEAN NOT NULL,

    account_expired BOOLEAN NOT NULL,

    credentials_expired BOOLEAN NOT NULL,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    version BIGINT NOT NULL DEFAULT 0,

    CONSTRAINT uk_user_email UNIQUE (email)

);
