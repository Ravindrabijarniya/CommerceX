CREATE TABLE orders (

    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    order_number VARCHAR(50) NOT NULL UNIQUE,

    user_id BIGINT NOT NULL,

    status VARCHAR(30) NOT NULL,


    shipping_full_name VARCHAR(100) NOT NULL,

    shipping_phone_number VARCHAR(20) NOT NULL,

    shipping_address_line1 VARCHAR(255) NOT NULL,

    shipping_address_line2 VARCHAR(255),

    shipping_city VARCHAR(100) NOT NULL,

    shipping_state VARCHAR(100) NOT NULL,

    shipping_postal_code VARCHAR(20) NOT NULL,

    shipping_country VARCHAR(100) NOT NULL,



    subtotal DECIMAL(19,2) NOT NULL,

    shipping_charge DECIMAL(19,2) NOT NULL,

    discount DECIMAL(19,2) NOT NULL,

    tax DECIMAL(19,2) NOT NULL,

    grand_total DECIMAL(19,2) NOT NULL,

    version BIGINT NOT NULL DEFAULT 0,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_orders_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)

);



CREATE TABLE order_items (

    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    order_id BIGINT NOT NULL,

--    product snapshot

    product_id BIGINT NOT NULL,

    product_name VARCHAR(255) NOT NULL,

    product_slug VARCHAR(255) NOT NULL,

    unit_price DECIMAL(19,2) NOT NULL,

    quantity INT NOT NULL,

    subtotal DECIMAL(19,2) NOT NULL,

    version BIGINT NOT NULL DEFAULT 0,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_order_items_order
        FOREIGN KEY (order_id)
        REFERENCES orders(id)

);



CREATE INDEX idx_orders_user
    ON orders(user_id);

CREATE INDEX idx_orders_status
    ON orders(status);

CREATE INDEX idx_orders_created_at
    ON orders(created_at);


CREATE INDEX idx_order_items_order
    ON order_items(order_id);

CREATE INDEX idx_order_items_product
    ON order_items(product_id);
