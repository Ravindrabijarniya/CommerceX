CREATE TABLE refresh_tokens
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    token VARCHAR(512) NOT NULL UNIQUE,

    user_id BIGINT NOT NULL,

    expires_at TIMESTAMP NOT NULL,

    revoked BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    version BIGINT,

    CONSTRAINT fk_refresh_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
);
