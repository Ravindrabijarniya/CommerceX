CREATE TABLE inventories
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    product_id BIGINT NOT NULL UNIQUE,

    stock_quantity INT NOT NULL,

    reserved_quantity INT NOT NULL,

    low_stock_threshold INT NOT NULL,

    stock_status VARCHAR(30) NOT NULL,

    version BIGINT NOT NULL DEFAULT 0,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_inventory_product
        FOREIGN KEY (product_id)
            REFERENCES products (id),

    CONSTRAINT chk_inventory_stock_quantity
        CHECK (stock_quantity >= 0),

    CONSTRAINT chk_inventory_reserved_quantity
        CHECK (reserved_quantity >= 0),

    CONSTRAINT chk_inventory_low_stock_threshold
        CHECK (low_stock_threshold >= 0),

    CONSTRAINT chk_inventory_reserved_not_exceed_stock
        CHECK (reserved_quantity <= stock_quantity)
);
