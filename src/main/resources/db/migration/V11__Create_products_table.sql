CREATE TABLE products (

    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    name VARCHAR(255) NOT NULL,

    slug VARCHAR(255) NOT NULL,

    short_description VARCHAR(500),

    description TEXT,

    brand VARCHAR(100),

    sku VARCHAR(100) NOT NULL,

    price DECIMAL(19,2) NOT NULL,

    thumbnail_url VARCHAR(500),

    featured BOOLEAN NOT NULL DEFAULT FALSE,

    status VARCHAR(20) NOT NULL,

    category_id BIGINT NOT NULL,

    version BIGINT NOT NULL DEFAULT 0,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT uk_products_slug
        UNIQUE (slug),

    CONSTRAINT uk_products_sku
        UNIQUE (sku),

    CONSTRAINT fk_products_category
        FOREIGN KEY (category_id)
        REFERENCES categories(id)
        ON DELETE RESTRICT
);

CREATE INDEX idx_products_category
    ON products(category_id);

CREATE INDEX idx_products_status
    ON products(status);

CREATE INDEX idx_products_featured
    ON products(featured);

CREATE INDEX idx_products_name
    ON products(name);
