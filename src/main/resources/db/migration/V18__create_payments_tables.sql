CREATE TABLE payments (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,


    payment_number VARCHAR(50)
        NOT NULL UNIQUE,


    order_id BIGINT
        NOT NULL UNIQUE,


    amount DECIMAL(12,2)
        NOT NULL,


    currency VARCHAR(10)
        NOT NULL,


    provider VARCHAR(30)
        NOT NULL,


    payment_method VARCHAR(30)
        NOT NULL,


    status VARCHAR(30)
        NOT NULL,


    provider_reference VARCHAR(100),


    idempotency_key VARCHAR(100)
        NOT NULL UNIQUE,


    completed_at DATETIME,


    failed_at DATETIME,


    version BIGINT,


    created_at DATETIME NOT NULL,


    updated_at DATETIME NOT NULL,


    CONSTRAINT fk_payment_order

        FOREIGN KEY(order_id)

        REFERENCES orders(id)

);


CREATE INDEX idx_payment_order
ON payments(order_id);


CREATE INDEX idx_payment_status
ON payments(status);


CREATE INDEX idx_payment_provider_reference
ON payments(provider_reference);


CREATE TABLE payment_transactions (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,


    payment_id BIGINT NOT NULL,


    attempt_number INT NOT NULL,


    provider_transaction_id VARCHAR(100),


    status VARCHAR(30) NOT NULL,


    provider_status VARCHAR(50),


    request_payload TEXT,


    response_payload TEXT,


    failure_reason VARCHAR(500),


    version BIGINT,


    created_at DATETIME NOT NULL,


    updated_at DATETIME NOT NULL,


    CONSTRAINT fk_payment_transaction_payment

    FOREIGN KEY(payment_id)

    REFERENCES payments(id)

);

CREATE INDEX idx_payment_transaction_payment

ON payment_transactions(payment_id);



CREATE INDEX idx_payment_transaction_provider

ON payment_transactions(provider_transaction_id);



CREATE TABLE payment_events (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,


    payment_id BIGINT NOT NULL,


    event_type VARCHAR(50)
        NOT NULL,


    message VARCHAR(500),


    metadata TEXT,


    occurred_at DATETIME NOT NULL,


    version BIGINT,


    created_at DATETIME NOT NULL,


    updated_at DATETIME NOT NULL,


    CONSTRAINT fk_payment_event_payment

    FOREIGN KEY(payment_id)

    REFERENCES payments(id)

);

CREATE INDEX idx_payment_event_payment

ON payment_events(payment_id);



CREATE INDEX idx_payment_event_type

ON payment_events(event_type);
