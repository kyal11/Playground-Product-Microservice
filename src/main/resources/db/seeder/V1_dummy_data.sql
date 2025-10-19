INSERT INTO categories (name, description)
VALUES
('Electronics', 'Electronic devices and gadgets'),
('Fashion', 'Clothing and accessories'),
('Books', 'Books and stationery');


INSERT INTO sub_categories (name, description, category_id)
VALUES
('Smartphones', 'All types of smartphones', 1),
('Laptops', 'Personal and gaming laptops', 1),
('Men Clothing', 'Men fashion apparel', 2),
('Women Clothing', 'Women fashion apparel', 2),
('Fiction', 'Fiction books', 3),
('Non-fiction', 'Non-fiction books', 3);


INSERT INTO products (name, description, price, stock, has_variant, sub_category_id)
VALUES
('iPhone 15 Pro', 'Latest Apple flagship smartphone', 1999.99, 50, TRUE, 1),
('Samsung Galaxy S24', 'Newest Samsung Galaxy series phone', 1499.99, 80, TRUE, 1),
('Asus ROG Laptop', 'Gaming laptop with RTX 4080', 2999.99, 20, FALSE, 2),
('Men T-Shirt', 'Cotton t-shirt for men', 19.99, 200, TRUE, 3),
('Women Dress', 'Elegant summer dress', 39.99, 150, TRUE, 4),
('Atomic Habits', 'Best-selling self-improvement book', 14.99, 120, FALSE, 6);


INSERT INTO product_images (product_id, image_url)
VALUES
(1, 'https://dummyimage.com/iphone15-front.png'),
(1, 'https://dummyimage.com/iphone15-back.png'),
(2, 'https://dummyimage.com/galaxys24-front.png'),
(3, 'https://dummyimage.com/asusrog.png'),
(4, 'https://dummyimage.com/men-tshirt.png'),
(5, 'https://dummyimage.com/women-dress.png'),
(6, 'https://dummyimage.com/atomic-habits.png');


INSERT INTO product_videos (product_id, video_url)
VALUES
(1, 'https://videos.com/iphone15-review.mp4'),
(2, 'https://videos.com/galaxys24-unboxing.mp4'),
(3, 'https://videos.com/asusrog-benchmark.mp4');


INSERT INTO product_variants (product_id, variant_name, variant_value, price, stock)
VALUES
(1, 'Storage', '128GB', 1999.99, 20),
(1, 'Storage', '256GB', 2199.99, 15),
(2, 'Color', 'Black', 1499.99, 40),
(2, 'Color', 'White', 1499.99, 40),
(4, 'Size', 'M', 19.99, 50),
(4, 'Size', 'L', 19.99, 50),
(5, 'Size', 'S', 39.99, 30),
(5, 'Size', 'M', 39.99, 40);

INSERT INTO reviews (product_id, user_id, rating, comment)
VALUES
(1, 101, 5, 'Absolutely love this iPhone!'),
(1, 102, 4, 'Great phone but battery could be better'),
(2, 101, 5, 'Samsung nailed it this time'),
(3, 103, 4, 'Beast for gaming, but expensive'),
(6, 105, 5, 'This book changed my life');
