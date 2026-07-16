INSERT INTO role_permissions(role_id,permission_id)

SELECT r.id,p.id

FROM roles r

JOIN permissions p

WHERE r.name='ROLE_ADMIN'

AND p.name='PRODUCT_CREATE';
