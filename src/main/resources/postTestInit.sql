drop table if exists connections;
drop table if exists posts;
drop table if exists users cascade;

CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    user_password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    is_business BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE connections (
    connection_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_A BIGINT,
    user_B BIGINT,
    FOREIGN KEY (user_A) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (user_B) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE posts (
    postId INT AUTO_INCREMENT PRIMARY KEY,
    postedBy BIGINT NOT NULL,
    postText VARCHAR(255) NOT NULL,
    timePostedEpoch BIGINT NOT NULL,
    isPrivate BOOLEAN
);

-- Insert initial Users
INSERT INTO users (username, user_password, email, first_name, last_name, is_business, created_at, updated_at)
VALUES
('A', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'A@gmail.com', 'TestUserA', 'A', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('B', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'B@gmail.com', 'TestUserB', 'B', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('C', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'C@gmail.com', 'TestUserC', 'C', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert connections
INSERT INTO connections (user_A, user_B)
VALUES
(1, 2),
(2, 1);

-- Insert posts
INSERT INTO posts (postedBy, postText, timePostedEpoch, isPrivate) 
VALUES
(1, 'User A Private Post', 123456789L, TRUE),
(1, 'User A Public Post', 123456789L, FALSE),
(2, 'User B Private Post', 123456789L, TRUE),
(2, 'User B Public Post', 123456789L, FALSE),
(3, 'User C Private Post', 123456789L, TRUE),
(3, 'User C Public Post', 123456789L, FALSE);