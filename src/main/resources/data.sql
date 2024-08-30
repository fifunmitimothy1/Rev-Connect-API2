-- Drop existing tables if they exist
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS user_liked_posts;
DROP TABLE IF EXISTS user_followed_hashtags;
DROP TABLE IF EXISTS user_following;
DROP TABLE IF EXISTS personal_profiles;
DROP TABLE IF EXISTS posts CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS hashtags CASCADE;
DROP TABLE IF EXISTS roles CASCADE;

-- Create roles table
CREATE TABLE roles (
    role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

-- Create users table
CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    user_password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    is_business BOOLEAN NOT NULL DEFAULT FALSE,
    account_type VARCHAR(15) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create hashtags table
CREATE TABLE hashtags (
    hashtag_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    hashtag_name VARCHAR(50) NOT NULL UNIQUE
);

-- Create posts table
CREATE TABLE posts (
    post_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    author_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create personal_profiles table
CREATE TABLE personal_profiles (
    user_id BIGINT NOT NULL,
    bio VARCHAR(255),
    PRIMARY KEY (user_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create user_liked_posts table
CREATE TABLE user_liked_posts (
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, post_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES posts(post_id) ON DELETE CASCADE
);

-- Create user_followed_hashtags table
CREATE TABLE user_followed_hashtags (
    user_id BIGINT NOT NULL,
    hashtag_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, hashtag_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (hashtag_id) REFERENCES hashtags(hashtag_id) ON DELETE CASCADE
);

-- Create user_following table
CREATE TABLE user_following (
    user_id BIGINT NOT NULL,
    following_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, following_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (following_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create user_roles table
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE
);

-- Insert sample roles
INSERT INTO roles (role_name) VALUES
('ROLE_USER'),
('ROLE_ADMIN');

-- Insert sample users
INSERT INTO users (username, user_password, email, first_name, last_name, is_business, account_type)
VALUES
('testuser1', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'test1@mail.com', 'test', 'tester', false, 'standard'),
('testuser2', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'test2@mail.com', 'test', 'tester', false, 'standard'),
('testuser3', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'test3@mail.com', 'test', 'tester', true, 'business'),
('testuser4', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'test4@mail.com', 'test', 'tester', false, 'standard');

-- Assign roles to users
INSERT INTO user_roles (user_id, role_id)
VALUES
(1, 1),
(1, 2),
(2, 1),
(3, 1),
(4, 1);

-- Insert personal profiles
INSERT INTO personal_profiles (user_id, bio)
VALUES
(1, 'TestBio1!'),
(2, 'TestBio2!'),
(3, 'TestBio3!'),
(4, 'TestBio4!');

-- Insert sample posts
INSERT INTO posts (author_id, title, content, created_at, updated_at)
VALUES
(1, 'First Post', 'This is the content of the first post.', '2024-08-20 14:30:00', '2024-08-20 14:30:00'),
(2, 'Morning Thoughts', 'Just sharing some morning thoughts.', '2024-08-21 09:00:00', '2024-08-21 09:00:00'),
(1, 'Another Post', 'Here is some more content.', '2024-08-21 10:15:00', '2024-08-21 10:15:00'),
(3, 'Lunch Break', 'Enjoying my lunch break.', '2024-08-22 12:45:00', '2024-08-22 12:45:00'),
(4, 'Afternoon Post', 'Some afternoon reflections.', '2024-08-22 16:00:00', '2024-08-22 16:00:00'),
(1, 'Yet Another Post', 'Adding another post to my collection.', '2024-08-23 11:30:00', '2024-08-23 11:30:00'),
(1, 'Midday Thoughts', 'Midday musings.', '2024-08-23 13:00:00', '2024-08-23 13:00:00'), -- Changed author_id from 5 to 1
(2, 'Early Morning', 'Starting the day early.', '2024-08-24 08:30:00', '2024-08-24 08:30:00'),
(3, 'Afternoon Musings', 'Reflecting on the day.', '2024-08-24 15:00:00', '2024-08-24 15:00:00'),
(4, 'Sunday Thoughts', 'Some thoughts for Sunday.', '2024-08-25 10:00:00', '2024-08-25 10:00:00'),
(1, 'Final Post', 'This is my last post.', '2024-08-26 14:30:00', '2024-08-26 14:30:00'),
(1, 'Evening Reflections', 'Wrapping up the day.', '2024-08-26 17:45:00', '2024-08-26 17:45:00'); -- Changed author_id from 5 to 1
 
