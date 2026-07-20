-- Composite index for featured product queries
CREATE INDEX idx_products_featured_status
ON products(featured, status);


-- Composite index for category browsing
CREATE INDEX idx_products_category_status
ON products(category_id, status);


-- Price filtering and sorting
CREATE INDEX idx_products_price
ON products(price);
