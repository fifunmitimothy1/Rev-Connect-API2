drop table if exists users cascade;

create table users(
    id BIGINT AUTO_INCREMENT PRIMARY KEY, 
    username VARCHAR(255) NOT NULL UNIQUE,
    firstName VARCHAR(255),  
    lastName VARCHAR(255),  
    email VARCHAR(255) NOT NULL UNIQUE, 
    userPwd VARCHAR(255) NOT NULL, 
    isBusiness BOOLEAN 
);
