CREATE TABLE categories (

    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    name VARCHAR(100) NOT NULL,

    description VARCHAR(500),

    slug VARCHAR(150) NOT NULL UNIQUE,

    status VARCHAR(20) NOT NULL,

    sort_order INT NOT NULL DEFAULT 0,

    parent_id BIGINT,

    version BIGINT NOT NULL DEFAULT 0,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT uk_category_parent_name
        UNIQUE(parent_id, name),

    CONSTRAINT fk_category_parent
        FOREIGN KEY(parent_id)
        REFERENCES categories(id)

);
