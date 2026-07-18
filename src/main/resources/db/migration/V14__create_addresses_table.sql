CREATE TABLE addresses (

    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    user_id BIGINT NOT NULL,

    full_name VARCHAR(100) NOT NULL,

    phone_number VARCHAR(20) NOT NULL,

    address_line1 VARCHAR(255) NOT NULL,

    address_line2 VARCHAR(255),

    city VARCHAR(100) NOT NULL,

    state VARCHAR(100) NOT NULL,

    postal_code VARCHAR(20) NOT NULL,

    country VARCHAR(100) NOT NULL,

    address_type VARCHAR(20) NOT NULL,

    default_address BOOLEAN NOT NULL DEFAULT FALSE,

    active BOOLEAN NOT NULL DEFAULT TRUE,

    version BIGINT NOT NULL DEFAULT 0,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_addresses_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)

);   -- <-- CREATE TABLE ends HERE

CREATE INDEX idx_addresses_user
    ON addresses(user_id);

CREATE INDEX idx_addresses_user_default
    ON addresses(user_id, default_address);

CREATE INDEX idx_addresses_user_active
    ON addresses(user_id, active);
