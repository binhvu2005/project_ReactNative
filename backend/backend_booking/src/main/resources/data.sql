-- SQL script to insert hardcoded data for hotel booking app
-- Run this after database is created

-- Insert a test user first (required for reviews)
-- Email: test@example.com
-- Password: password123 (BCrypt hash: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy)

-- Delete all existing reviews first (to avoid foreign key issues)
DELETE FROM reviews WHERE user_id = 1;

-- Delete existing user with id=1 if exists
DELETE FROM users WHERE id = 1;

-- Reset AUTO_INCREMENT to ensure id=1 can be used
ALTER TABLE users AUTO_INCREMENT = 1;

-- Create test user with id=1
INSERT INTO users (id, email, phone, password_hash, is_verified, provider, created_at, updated_at) VALUES
(1, 'test@example.com', '1234567890', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', true, 'local', NOW(), NOW());

-- Set user_id variable for use in reviews
SET @test_user_id = 1;

-- Insert Hotels
INSERT INTO hotels (name, description, address, city, country, star, lat, lng, created_at, updated_at) VALUES
('Malon Greens', 'Luxury hotel with modern amenities and excellent service. Perfect for business and leisure travelers.', '123 Marine Drive', 'Mumbai', 'India', 5, 19.0760, 72.8777, NOW(), NOW()),
('Fortune Lan', 'Premium beachfront hotel with stunning ocean views and world-class facilities.', 'Goa Beach Road', 'Goa', 'India', 5, 15.2993, 74.1240, NOW(), NOW()),
('Sabro Prime', 'Contemporary hotel in the heart of the city with elegant rooms and exceptional hospitality.', '456 Business District', 'Mumbai', 'India', 5, 19.0760, 72.8777, NOW(), NOW()),
('Peradise Mint', 'Boutique hotel offering unique design and personalized service for discerning guests.', '789 Heritage Street', 'Mumbai', 'India', 4, 19.0760, 72.8777, NOW(), NOW()),
('Grand Plaza', 'Spacious hotel with modern facilities and convenient location near major attractions.', '321 Main Street', 'Chennai', 'India', 4, 13.0827, 80.2707, NOW(), NOW()),
('Royal Heritage', 'Historic hotel combining traditional architecture with modern comforts.', 'Palace Road', 'Jaipur', 'India', 5, 26.9124, 75.7873, NOW(), NOW()),
('Ocean Breeze', 'Beachfront resort with tropical ambiance and excellent dining options.', 'Beach Resort Area', 'Puri', 'India', 4, 19.8136, 85.8312, NOW(), NOW());

-- Insert Rooms for Malon Greens (id=1)
-- Note: area_m2 column may not exist, so we omit it or use areaM2 if that's the actual column name
INSERT INTO rooms (hotel_id, code, title, description, price_cents, currency, capacity, beds, status, created_at, updated_at) VALUES
(1, 'MG-101', 'Deluxe King Room', 'Spacious room with king-size bed, city view, and modern amenities', 12000, 'USD', 2, 1, 'available', NOW(), NOW()),
(1, 'MG-102', 'Executive Suite', 'Luxury suite with separate living area and premium furnishings', 18000, 'USD', 4, 2, 'available', NOW(), NOW()),
(1, 'MG-103', 'Standard Twin Room', 'Comfortable room with twin beds, perfect for families', 10000, 'USD', 2, 2, 'available', NOW(), NOW());

-- Insert Rooms for Fortune Lan (id=2)
INSERT INTO rooms (hotel_id, code, title, description, price_cents, currency, capacity, beds, status, created_at, updated_at) VALUES
(2, 'FL-201', 'Ocean View Suite', 'Stunning beachfront suite with private balcony and ocean views', 15000, 'USD', 2, 1, 'available', NOW(), NOW()),
(2, 'FL-202', 'Deluxe Beach Room', 'Spacious room with direct beach access and modern amenities', 13000, 'USD', 2, 1, 'available', NOW(), NOW()),
(2, 'FL-203', 'Family Villa', 'Large villa with multiple bedrooms, perfect for families', 25000, 'USD', 6, 3, 'available', NOW(), NOW());

-- Insert Rooms for Sabro Prime (id=3)
INSERT INTO rooms (hotel_id, code, title, description, price_cents, currency, capacity, beds, status, created_at, updated_at) VALUES
(3, 'SP-301', 'Business Class Room', 'Modern room designed for business travelers with work desk', 9000, 'USD', 2, 1, 'available', NOW(), NOW()),
(3, 'SP-302', 'Junior Suite', 'Comfortable suite with separate seating area', 14000, 'USD', 2, 1, 'available', NOW(), NOW()),
(3, 'SP-303', 'Standard Double Room', 'Cozy room with double bed and city view', 8500, 'USD', 2, 1, 'available', NOW(), NOW());

-- Insert Rooms for Peradise Mint (id=4)
INSERT INTO rooms (hotel_id, code, title, description, price_cents, currency, capacity, beds, status, created_at, updated_at) VALUES
(4, 'PM-401', 'Boutique Deluxe Room', 'Uniquely designed room with artistic decor and premium amenities', 12000, 'USD', 2, 1, 'available', NOW(), NOW()),
(4, 'PM-402', 'Designer Suite', 'Stylish suite featuring contemporary design elements', 16000, 'USD', 2, 1, 'available', NOW(), NOW()),
(4, 'PM-403', 'Classic Room', 'Elegant room with traditional touches and modern comfort', 11000, 'USD', 2, 1, 'available', NOW(), NOW());

-- Insert Room Images
-- Malon Greens Room Images
INSERT INTO room_images (room_id, url, is_primary, created_at) VALUES
(1, 'https://images.unsplash.com/photo-1566073771259-6a8506099945?w=800', true, NOW()),
(1, 'https://images.unsplash.com/photo-1578683010236-d716f9a3f461?w=800', false, NOW()),
(2, 'https://images.unsplash.com/photo-1618220179428-22790b461013?w=800', true, NOW()),
(2, 'https://images.unsplash.com/photo-1590490360182-c33d57733427?w=800', false, NOW()),
(3, 'https://images.unsplash.com/photo-1566665797739-1674de7a421a?w=800', true, NOW());

-- Fortune Lan Room Images
INSERT INTO room_images (room_id, url, is_primary, created_at) VALUES
(4, 'https://images.unsplash.com/photo-1578683010236-d716f9a3f461?w=800', true, NOW()),
(4, 'https://images.unsplash.com/photo-1618220179428-22790b461013?w=800', false, NOW()),
(5, 'https://images.unsplash.com/photo-1590490360182-c33d57733427?w=800', true, NOW()),
(5, 'https://images.unsplash.com/photo-1566073771259-6a8506099945?w=800', false, NOW()),
(6, 'https://images.unsplash.com/photo-1566665797739-1674de7a421a?w=800', true, NOW());

-- Sabro Prime Room Images
INSERT INTO room_images (room_id, url, is_primary, created_at) VALUES
(7, 'https://images.unsplash.com/photo-1618220179428-22790b461013?w=800', true, NOW()),
(7, 'https://images.unsplash.com/photo-1566073771259-6a8506099945?w=800', false, NOW()),
(8, 'https://images.unsplash.com/photo-1590490360182-c33d57733427?w=800', true, NOW()),
(8, 'https://images.unsplash.com/photo-1578683010236-d716f9a3f461?w=800', false, NOW()),
(9, 'https://images.unsplash.com/photo-1566665797739-1674de7a421a?w=800', true, NOW());

-- Peradise Mint Room Images
INSERT INTO room_images (room_id, url, is_primary, created_at) VALUES
(10, 'https://images.unsplash.com/photo-1566073771259-6a8506099945?w=800', true, NOW()),
(10, 'https://images.unsplash.com/photo-1618220179428-22790b461013?w=800', false, NOW()),
(11, 'https://images.unsplash.com/photo-1590490360182-c33d57733427?w=800', true, NOW()),
(11, 'https://images.unsplash.com/photo-1578683010236-d716f9a3f461?w=800', false, NOW()),
(12, 'https://images.unsplash.com/photo-1566665797739-1674de7a421a?w=800', true, NOW());

-- Insert Reviews (using the test user_id variable)
-- Reviews for Malon Greens rooms
INSERT INTO reviews (room_id, user_id, rating, title, content, created_at) VALUES
(1, @test_user_id, 5, 'Excellent Stay!', 'Amazing hotel with great service and comfortable rooms. Highly recommend!', NOW()),
(1, @test_user_id, 5, 'Perfect Location', 'Great location with beautiful views. Room was clean and well-maintained.', NOW()),
(1, @test_user_id, 5, 'Outstanding Experience', 'One of the best hotels I have stayed at. Staff was very helpful.', NOW()),
(2, @test_user_id, 5, 'Luxury at its Best', 'The suite was spacious and luxurious. Worth every penny!', NOW()),
(2, @test_user_id, 5, 'Exceptional Service', 'Staff went above and beyond to make our stay comfortable.', NOW()),
(3, @test_user_id, 4, 'Good Value', 'Nice room for the price. Clean and comfortable.', NOW()),
(3, @test_user_id, 4, 'Family Friendly', 'Perfect for families. Kids loved the room.', NOW()),
(3, @test_user_id, 4, 'Comfortable Stay', 'Good amenities and clean facilities. Will come back.', NOW());

-- Reviews for Fortune Lan rooms
INSERT INTO reviews (room_id, user_id, rating, title, content, created_at) VALUES
(4, @test_user_id, 5, 'Beautiful Ocean View', 'Stunning views and excellent service. The beach access was perfect.', NOW()),
(4, @test_user_id, 5, 'Paradise Found', 'This hotel exceeded all expectations. The room was amazing!', NOW()),
(4, @test_user_id, 5, 'Best Beach Hotel', 'Perfect location right on the beach. Highly recommend!', NOW()),
(5, @test_user_id, 5, 'Relaxing Stay', 'Great place to unwind. The room was comfortable and clean.', NOW()),
(5, @test_user_id, 4, 'Good Beach Access', 'Nice room with easy access to the beach.', NOW()),
(6, @test_user_id, 5, 'Perfect for Families', 'The villa was spacious and perfect for our family vacation.', NOW());

-- Reviews for Sabro Prime rooms
INSERT INTO reviews (room_id, user_id, rating, title, content, created_at) VALUES
(7, @test_user_id, 5, 'Business Traveler Friendly', 'Great for business trips. Good WiFi and work space.', NOW()),
(7, @test_user_id, 5, 'Modern and Clean', 'Very modern hotel with excellent facilities.', NOW()),
(7, @test_user_id, 4, 'Comfortable Business Stay', 'Good location for business. Room was comfortable.', NOW()),
(8, @test_user_id, 5, 'Luxury Suite', 'The suite was beautiful and spacious. Great value!', NOW()),
(8, @test_user_id, 4, 'Nice Suite', 'Comfortable suite with good amenities.', NOW()),
(9, @test_user_id, 4, 'Good Standard Room', 'Clean and comfortable room for the price.', NOW()),
(9, @test_user_id, 4, 'Value for Money', 'Good room with basic amenities. Satisfied with the stay.', NOW());

-- Reviews for Peradise Mint rooms
INSERT INTO reviews (room_id, user_id, rating, title, content, created_at) VALUES
(10, @test_user_id, 4, 'Unique Design', 'Loved the artistic design of the room. Very unique!', NOW()),
(10, @test_user_id, 4, 'Boutique Experience', 'Nice boutique hotel with character. Room was comfortable.', NOW()),
(10, @test_user_id, 4, 'Stylish Room', 'Great design and good amenities. Enjoyed the stay.', NOW()),
(11, @test_user_id, 5, 'Designer Suite Excellence', 'Beautiful suite with amazing design elements. Highly recommend!', NOW()),
(11, @test_user_id, 4, 'Modern Suite', 'Nice suite with contemporary design. Comfortable stay.', NOW()),
(12, @test_user_id, 4, 'Classic Elegance', 'Elegant room with traditional touches. Very comfortable.', NOW()),
(12, @test_user_id, 4, 'Comfortable Classic Room', 'Good room with classic design. Clean and well-maintained.', NOW());

