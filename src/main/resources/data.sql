DROP TABLE IF EXISTS business_profile;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    email VARCHAR(255),
    is_business BOOLEAN DEFAULT false
);

CREATE TABLE business_profile (
    profile_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT UNIQUE NOT NULL REFERENCES users(id),
    bio_text VARCHAR(500)
);

INSERT INTO users VALUES (111, 'test1', 'password1', 'joe1', 'doe1', 'test1@email', true);
INSERT INTO users VALUES (112, 'test2', 'password2', 'joe2', 'doe2', 'test2@email', true);
INSERT INTO users VALUES (113, 'test3', 'password3', 'joe3', 'doe3', 'test3@email', true);
INSERT INTO users VALUES (114, 'test4', 'password4', 'joe4', 'doe4', 'test4@email', false);
INSERT INTO users VALUES (115, 'test5', 'password5', 'joe5', 'doe5', 'test5@email', false);
INSERT INTO users VALUES (116, 'test6', 'password6', 'joe6', 'doe6', 'test6@email', false);

INSERT INTO business_profile VALUES (999, 111, 'Test Bio 1');
INSERT INTO business_profile VALUES (998, 112, 'Test Bio 2');
INSERT INTO business_profile VALUES (997, 113, 'Test Bio 3');
INSERT INTO business_profile VALUES (996, 114, 'Test Bio 4');
INSERT INTO business_profile VALUES (995, 115, 'Test Bio 5');
INSERT INTO business_profile VALUES (994, 116, 'Test Bio 6');

-- Dropping tables if they exist
DROP TABLE IF EXISTS endorsement_links;

-- Creating endorsement_links table
CREATE TABLE endorsement_links (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    link VARCHAR(255) NOT NULL,
    link_text VARCHAR(255),

    FOREIGN KEY (user_id) REFERENCES business_profile(user_id)
);

-- Sample data for endorsement_links
INSERT INTO endorsement_links VALUES (1, 111, 'Https://www.blahblablah.com', 'test_link');

-- Dropping tables if they exist
DROP TABLE IF EXISTS partner_channels;

-- Creating partner_channels table
CREATE TABLE partner_channels (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL,

    FOREIGN KEY (user_id) REFERENCES business_profile(user_id)
);

-- Sample data for partner_channels
INSERT INTO partner_channels (user_id, name, url) VALUES 
(111, 'Partner Channel 1', 'https://www.partnerchannel1.com'),
(112, 'Partner Channel 2', 'https://www.partnerchannel2.com'),
(113, 'Partner Channel 3', 'https://www.partnerchannel3.com');
