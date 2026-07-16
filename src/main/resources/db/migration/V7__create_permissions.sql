CREATE TABLE permissions (

    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    name VARCHAR(100) NOT NULL UNIQUE,

    description VARCHAR(255),

    created_at TIMESTAMP,

    updated_at TIMESTAMP

);

CREATE TABLE role_permissions (

    role_id BIGINT NOT NULL,

    permission_id BIGINT NOT NULL,

    PRIMARY KEY(role_id, permission_id),

    CONSTRAINT fk_role_permission_role
        FOREIGN KEY(role_id)
        REFERENCES roles(id),

    CONSTRAINT fk_role_permission_permission
        FOREIGN KEY(permission_id)
        REFERENCES permissions(id)

);
