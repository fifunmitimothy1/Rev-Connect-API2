drop table if exists user_roles;
drop table if exists users cascade;
drop table if exists personal_profiles;
drop table if exists posts cascade;

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
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE personal_profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNIQUE,
    bio VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE posts (
    post_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    author_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Insert users
-- passwords are hashed from "hashed_password"
INSERT INTO users (user_id, username, user_password, email, first_name, last_name, is_business, account_type, created_at, updated_at)
VALUES
(1, 'anjineyulu215', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'anjineyulu215@revature.net', 'Anjineyulu', 'Singh', false, 'personal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'asif650', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'asif650@revature.net', 'Asif', 'Khan', false, 'personal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'benjamin346', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'benjamin346@revature.net', 'Benjamin', 'Smith', true, 'business', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'christopherjoseph850', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'christopherjoseph850@revature.net', 'Christopher', 'Joseph', false, 'personal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'gautam746', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'gautam746@revature.net', 'Gautam', 'Reddy', false, 'personal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'matt392', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'matt392@revature.net', 'Matt', 'Johnson', false, 'personal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 'mohamed019', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'mohamed019@revature.net', 'Mohamed', 'Ali', false, 'personal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 'mohan863', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'mohan863@revature.net', 'Mohan', 'Sharma', false, 'personal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 'natnael035', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'natnael035@revature.net', 'Natnael', 'Gebre', false, 'personal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 'olufifunmi957', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'olufifunmi957@revature.net', 'Olufifunmi', 'Adekunle', false, 'personal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 'rachana153', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'rachana153@revature.net', 'Rachana', 'Patel', false, 'personal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 'trevor689', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'trevor689@revature.net', 'Trevor', 'Brown', false, 'personal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 'yonas905', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'yonas905@revature.net', 'Yonas', 'Mekonnen', false, 'personal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 'nickolas.jurczak', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'nickolas.jurczak@revature.com', 'Nickolas', 'Jurczak', false, 'personal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 'phone329', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'phone329@revature.net', 'Phone', 'Smith', false, 'personal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 'sarangi604', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'sarangi604@revature.net', 'Sarangi', 'Mehta', false, 'personal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Assign roles to users
INSERT INTO user_roles (user_id, role)
VALUES
(1, 'ROLE_USER'),
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER'),
(3, 'ROLE_USER'),
(4, 'ROLE_USER');

INSERT INTO personal_profiles (user_id, bio)
VALUES
(1, 'TestBio1!'),
(2, 'TestBio2!'),
(3, 'TestBio3!'),
(4, 'TestBio4!');

-- Insert posts
INSERT INTO posts (author_id, title, content, created_at, updated_at)
VALUES
(1, 'testtitle1', 'This is the first test post.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'testtitle2', 'This is the second test post.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'testtitle3', 'Another post for testing.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'testtitle4', 'Yet another test post.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


ALTER SEQUENCE user_sequence RESTART WITH 5;
ALTER SEQUENCE post_sequence RESTART WITH 5;

-- create a table for  connection_requests
CREATE TABLE connection_requests(
    connection_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    requester_id BIGINT NOT NULL,
    recipient_id BIGINT NOT NULL,
    status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (requester_id) REFERENCES users(user_id),
    FOREIGN KEY (recipient_id) REFERENCES users(user_id)
);

--sample data for connection_requests table
INSERT INTO connection_requests (requester_id, recipient_id, status) VALUES
(1, 2, 'PENDING'),      
(3, 4, 'ACCEPTED'),     
(5, 6, 'REJECTED'),     
(7, 8, 'PENDING'),     
(9, 10, 'PENDING'),     
(11, 12, 'ACCEPTED'),   
(13, 14, 'PENDING'),    
(15, 16, 'REJECTED'),   
(1, 3, 'PENDING'),     
(3, 2, 'ACCEPTED'),     
(3, 6, 'ACCEPTED');   