CREATE TABLE roles (

    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    name VARCHAR(50) NOT NULL,

    description VARCHAR(255) NOT NULL,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    version BIGINT NOT NULL DEFAULT 0,

    CONSTRAINT uk_role_name UNIQUE (name)

);
