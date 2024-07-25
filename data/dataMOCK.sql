
-- Drop the database if it already exists
DROP DATABASE IF EXISTS `MOCK`;
-- Create database
CREATE DATABASE IF NOT EXISTS `MOCK`;
USE `MOCK`;

-- Bảng Roles
CREATE TABLE `roles` (
  role_id INT PRIMARY KEY AUTO_INCREMENT,
  role_name VARCHAR(50) NOT NULL UNIQUE
);

-- Thêm các vai trò mặc định vào bảng Roles
INSERT INTO `roles` (role_name) VALUES ('ADMIN');
INSERT INTO `roles` (role_name) VALUES ('LIBRARIAN');
INSERT INTO `roles` (role_name) VALUES ('USER');

-- Bảng Users
CREATE TABLE `users` (
  user_id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL UNIQUE,
  `password` VARCHAR(100) NOT NULL,
  email VARCHAR(100) UNIQUE,
  full_name VARCHAR(100) NOT NULL,
  phone VARCHAR(15) UNIQUE,
  address VARCHAR(250),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO `users` (username, `password`, email, full_name, phone, address)
VALUES
('TRINH123', '$2a$10$gz4iOFHxapM0PkXmUFvI4Or/dJJ5lN6Zcm5t4fF5.6VTVICwv/zDS', 'trinh123@example.com', 'JNguyen Van Trinh', '0899633453', 'Quang Binh'),
('john_doe', '$2a$10$gz4iOFHxapM0PkXmUFvI4Or/dJJ5lN6Zcm5t4fF5.6VTVICwv/zDS', 'john1@example.com', 'John Doe', '0899633123', 'Quang Binh'),
('john_doe2', '$2a$10$gz4iOFHxapM0PkXmUFvI4Or/dJJ5lN6Zcm5t4fF5.6VTVICwv/zDS', 'john2@example.com', 'John Doe', '0899633124', 'Quang Binh'),
('john_doe22', '$2a$10$gz4iOFHxapM0PkXmUFvI4Or/dJJ5lN6Zcm5t4fF5.6VTVICwv/zDS', 'john22@example.com', 'John Doe', '0899633125', 'Quang Binh');

CREATE TABLE user_roles (
	user_id INT NOT NULL,
    role_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id),
    PRIMARY KEY (user_id, role_id)
);

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1),(2, 2),(3, 3),(4, 3), (2, 3);

CREATE TABLE publisher (
    publisher_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20)
);

INSERT INTO publisher (name, address, phone)
VALUES 
('Publisher 1', '123 Publisher St', '123-456-7890'),
('Publisher 2', '124 Publisher St', '123-456-7891'),
('Publisher 3', '125 Publisher St', '123-456-7892'),
('Publisher 4', '126 Publisher St', '123-456-7893'),
('Publisher 5', '456 Publisher Ave', '987-654-3210');

-- Bảng Books
CREATE TABLE `books` (
  book_id INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  author_id INT NOT NULL,
  genre_id INT NOT NULL,
  publication_year INT NOT NULL,
  quantity INT NOT NULL,
  publisher_id INT,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (publisher_id) REFERENCES publisher(publisher_id),
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  
);
INSERT INTO `books` (title, author_id, genre, publication_year, quantity, created_at, updated_at)
VALUES ('Sample Book Title', 1, 'Fiction', 2023, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Bảng Notifications
CREATE TABLE `notifications` (
  notification_id INT PRIMARY KEY AUTO_INCREMENT,
  notification_content VARCHAR(255) NOT NULL,
  user_id INT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(user_id)
);
-- Bảng BorrowRecords
CREATE TABLE `borrow_records` (
  borrow_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  book_id INT NOT NULL,
  borrow_date DATE NOT NULL,
  due_date DATE NOT NULL,
  return_date DATE DEFAULT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(user_id),
  FOREIGN KEY (book_id) REFERENCES books(book_id)
);

INSERT INTO `borrow_records` (user_id, book_id, borrow_date, due_date, return_date, created_at, updated_at)
VALUES 
(1, 1, '2024-05-20', '2024-06-20', '2024-06-20', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

CREATE TABLE payment_record (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    amount DOUBLE NOT NULL,
    payment_date DATETIME NOT NULL,
    borrow_id INT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_borrow_record FOREIGN KEY (borrow_id) REFERENCES borrow_records(borrow_id)
);



