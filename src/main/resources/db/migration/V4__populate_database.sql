INSERT INTO categories (name)
VALUES ('Fruits'),
       ('Vegetables'),
       ('Dairy'),
       ('Bakery'),
       ('Beverages');

INSERT INTO products (name, price, description, category_id)
VALUES
-- Fruits
('Banana (1kg)', 3.50, 'Fresh Cavendish bananas, perfect for snacking or smoothies.', 1),
('Granny Smith Apple (1kg)', 4.20, 'Crisp and tangy Australian-grown Granny Smith apples.', 1),

-- Vegetables
('Carrots (1kg)', 2.80, 'Sweet, crunchy carrots ideal for salads and cooking.', 2),
('Broccoli (each)', 3.00, 'Fresh green broccoli, rich in vitamins and fibre.', 2),

-- Dairy
('Full Cream Milk (2L)', 3.60, 'Australian full cream cow’s milk, pasteurised and homogenised.', 3),
('Cheddar Cheese Block (500g)', 6.50, 'Mature cheddar cheese block, great for sandwiches and cooking.', 3),

-- Bakery
('White Sandwich Bread (700g)', 3.20, 'Soft white bread loaf, perfect for toast or sandwiches.', 4),
('Chocolate Muffins (4 pack)', 5.50, 'Moist chocolate muffins baked fresh daily.', 4),

-- Beverages
('Coca-Cola 1.25L Bottle', 2.50, 'Classic carbonated soft drink, best served chilled.', 5),
('Orange Juice (2L)', 5.00, '100% pure squeezed orange juice, no added sugar.', 5);
