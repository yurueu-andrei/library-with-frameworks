INSERT INTO users (first_name, last_name, passport, email, password, address, birth_date, user_status)
VALUES ('vlad', 'kaliaha', '1111', 'email1', '$2a$12$Uagtw7S34f0.YNwPIJgIOu8OE5JGY9Oor1E9vX2Bf5T3j24P5qLP2',  'address1', '2005-06-06', 'ACTIVE'),
       ('andrei', 'yurueu', '2222', 'email2', '$2a$12$31P2DhEMk1Wfs.9aIQn92unceFrbn.d2DFkX583UI/V9AATctMztC', 'address2', '2001-06-06', 'ACTIVE'),
       ('yaroslav', 'vasilevski', '3333', 'email3', '345', 'address3', '1998-06-06', 'ACTIVE'),
       ('anastasiya', 'yurkova', '4444', 'email4', '567', 'address4', '1999-06-06', 'ACTIVE'),
       ('alexander', 'kupriyanenko', '5555', 'email5', '789', 'address5', '1996-06-06', 'ACTIVE');

INSERT INTO roles (role_name)
VALUES ('admin'),
       ('user');

INSERT INTO authorities (authority_name)
VALUES ('AUTHOR_WRITE'),
       ('AUTHOR_DELETE'),
       ('BOOK_WRITE'),
       ('BOOK_DELETE'),
       ('BOOK_COPY_WRITE'),
       ('BOOK_COPY_DELETE'),
       ('BOOK_DAMAGE_WRITE'),
       ('BOOK_DAMAGE_DELETE'),
       ('ORDER_READ'),
       ('ORDER_WRITE'),
       ('ORDER_DELETE'),
       ('ROLE_READ'),
       ('USER_READ'),
       ('USER_WRITE'),
       ('USER_DELETE');

INSERT INTO user_role_links (user_id, role_id)
VALUES (1, 1),
       (2, 2),
       (3, 2),
       (4, 2),
       (5, 2);

INSERT INTO authority_role_links (role_id, authority_id)
VALUES (1, 1),      (2, 10),
       (1, 2),      (2, 11),
       (1, 3),      (2, 14),
       (1, 4),      (2, 15),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (1, 10),
       (1, 11),
       (1, 12),
       (1, 13),
       (1, 14),
       (1, 15);

INSERT INTO orders (order_status, start_date, price, end_date, user_id)
VALUES ('NEW', '1998-06-06', '243', '1998-06-06', '1'),
       ('NEW', '1998-06-06', '21', '1998-06-06', '1'),
       ('NEW', '1998-06-06', '253', '1998-06-06', '1'),
       ('NEW', '1998-06-06', '273', '1998-06-06', '3'),
       ('NEW', '1998-06-06', '238', '1998-06-06', '4');

INSERT INTO books (title, pages, image_path, book_status)
VALUES ('War and peace', '1365', 'image path', 'ACTIVE'),
       ('The Master and Margarita', '638', 'image path', 'ACTIVE'),
       ('Idiot', '496', 'image path', 'ACTIVE'),
       ('The old man and the sea', '153', 'image path', 'ACTIVE'),
       ('Eugene Onegin', '462', 'image path', 'ACTIVE');

INSERT INTO book_copies (book_copy_status, registration_date, image_path, price_per_day, book_id)
VALUES ('AVAILABLE', '2019-03-01', 'image path', '150', 1),
       ('AVAILABLE', '2020-06-01', 'image path', '210', 2),
       ('AVAILABLE', '2021-08-04', 'image path', '225', 2),
       ('AVAILABLE', '2017-10-10', 'image path', '128', 5),
       ('AVAILABLE', '2020-06-02', 'image path', '311', 3);

INSERT INTO order_book_copy_links (order_id, book_copy_id)
VALUES (1, 3),
       (1, 2),
       (3, 4),
       (4, 1),
       (5, 5);

INSERT INTO genres (genre_name)
VALUES ('NOVEL'),
       ('ADVENTURE'),
       ('COMEDY'),
       ('CRIME'),
       ('HORROR'),
       ('SCIENCE FICTION'),
       ('ROMANCE');

INSERT INTO book_genre_links (book_id, genre_id)
VALUES (1, 3),
       (2, 1),
       (3, 1),
       (4, 4),
       (5, 2);

INSERT INTO authors (first_name, last_name, birth_date, image_path, author_status)
VALUES ('Lev', 'Tolstoy', '1879-04-04', 'image path', 'ACTIVE'),
       ('Ernest', 'Hemingway', '1903-07-07', 'image path', 'ACTIVE'),
       ('Mikhail', 'Bulgakov', '1885-10-10', 'image path', 'ACTIVE'),
       ('Alexander', 'Pushkin', '1852-02-02', 'image path', 'ACTIVE'),
       ('Fedor', 'Dostoevsky', '1845-01-01', 'image path', 'ACTIVE');

INSERT INTO book_author_links (book_id, author_id)
VALUES (1, 1),
       (2, 3),
       (3, 5),
       (4, 2),
       (5, 4);

INSERT INTO book_damage (image_path, damage_description, user_id, order_id, book_copy_id, book_damage_status)
VALUES ('image path', 'damage1', '1', '1', '3', 'ACTIVE'),
       ('image path', 'damage2', '1', '1', '2', 'ACTIVE'),
       ('image path', 'damage3', '3', '4', '1', 'ACTIVE'),
       ('image path', 'damage4', '4', '5', '5', 'ACTIVE');