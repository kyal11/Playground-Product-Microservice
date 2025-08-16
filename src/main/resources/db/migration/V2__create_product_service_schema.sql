-- =========================================
--  Function for auto-updating updated_at
-- =========================================
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
   NEW.updated_at = NOW();
   RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- =========================================
--  Categories Table
-- =========================================
CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    deleted_at TIMESTAMP WITH TIME ZONE
);

CREATE TRIGGER trg_update_categories
BEFORE UPDATE ON categories
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

CREATE INDEX idx_categories_name ON categories(name);

-- =========================================
--  Sub Categories Table
-- =========================================
CREATE TABLE sub_categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    category_id INT NOT NULL REFERENCES categories(id) ON DELETE CASCADE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    deleted_at TIMESTAMP WITH TIME ZONE
);

CREATE TRIGGER trg_update_sub_categories
BEFORE UPDATE ON sub_categories
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

CREATE INDEX idx_sub_categories_name ON sub_categories(name);
CREATE INDEX idx_sub_categories_category_id ON sub_categories(category_id);

-- =========================================
--  Products Table
-- =========================================
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price NUMERIC(15,2) NOT NULL,
    stock INT,
    has_variant BOOLEAN DEFAULT FALSE,
    sub_category_id INT NOT NULL REFERENCES sub_categories(id) ON DELETE CASCADE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    deleted_at TIMESTAMP WITH TIME ZONE
);

CREATE TRIGGER trg_update_products
BEFORE UPDATE ON products
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

CREATE INDEX idx_products_name ON products(name);
CREATE INDEX idx_products_sub_category_id ON products(sub_category_id);

-- =========================================
--  Product Images Table
-- =========================================
CREATE TABLE product_images (
    id SERIAL PRIMARY KEY,
    product_id INT NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    image_url TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    deleted_at TIMESTAMP WITH TIME ZONE
);

CREATE TRIGGER trg_update_product_images
BEFORE UPDATE ON product_images
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

CREATE INDEX idx_product_images_product_id ON product_images(product_id);

-- =========================================
--  Product Videos Table
-- =========================================
CREATE TABLE product_videos (
    id SERIAL PRIMARY KEY,
    product_id INT NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    video_url TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    deleted_at TIMESTAMP WITH TIME ZONE
);

CREATE TRIGGER trg_update_product_videos
BEFORE UPDATE ON product_videos
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

CREATE INDEX idx_product_videos_product_id ON product_videos(product_id);

-- =========================================
--  Product Variants Table
-- =========================================
CREATE TABLE product_variants (
    id SERIAL PRIMARY KEY,
    product_id INT NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    variant_name VARCHAR(100) NOT NULL,
    variant_value VARCHAR(100) NOT NULL,
    price NUMERIC(15,2),
    stock INT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    deleted_at TIMESTAMP WITH TIME ZONE
);

CREATE TRIGGER trg_update_product_variants
BEFORE UPDATE ON product_variants
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

CREATE INDEX idx_product_variants_product_id ON product_variants(product_id);

-- =========================================
--  Reviews Table
-- =========================================
CREATE TABLE reviews (
    id SERIAL PRIMARY KEY,
    product_id INT NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL, -- Ambil dari Microservice Users
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    deleted_at TIMESTAMP WITH TIME ZONE
);

CREATE TRIGGER trg_update_reviews
BEFORE UPDATE ON reviews
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

CREATE INDEX idx_reviews_product_id ON reviews(product_id);
CREATE INDEX idx_reviews_user_id ON reviews(user_id);
