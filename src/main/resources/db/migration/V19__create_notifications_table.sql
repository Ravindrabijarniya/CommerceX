CREATE TABLE notifications (

    id BIGINT NOT NULL AUTO_INCREMENT,

    user_id BIGINT NOT NULL,

    type VARCHAR(50) NOT NULL,

    channel VARCHAR(50) NOT NULL,

    title VARCHAR(255) NOT NULL,

    message TEXT NOT NULL,

    status VARCHAR(50) NOT NULL,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    version BIGINT NOT NULL,

    PRIMARY KEY (id)

);


CREATE INDEX idx_notification_user
ON notifications(user_id);


CREATE INDEX idx_notification_status
ON notifications(status);
