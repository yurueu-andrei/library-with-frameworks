INSERT INTO users (first_name, last_name, passport, email, address, birth_date, delete_status)
VALUES ('vlad', 'kaliaha', '1111', 'email1', 'address1', '2005-06-06', 'EXISTS'),
       ('andrei', 'yurueu', '2222', 'email2', 'address2', '2001-06-06', 'EXISTS'),
       ('yaroslav', 'vasilevski', '3333', 'email3', 'address3', '1998-06-06', 'EXISTS'),
       ('anastasiya', 'yurkova', '4444', 'email4', 'address4', '1999-06-06', 'EXISTS'),
       ('alexander', 'kupriyanenko', '5555', 'email5', 'address5', '1996-06-06', 'EXISTS');

INSERT INTO roles (role_name)
VALUES ('admin'),
       ('user');

INSERT INTO user_role_links (user_id, role_id)
VALUES (1, 1),
       (2, 2),
       (3, 2),
       (4, 2),
       (5, 2);

INSERT INTO orders (order_status, start_date, price, end_date, user_id, delete_status)
VALUES ('NEW', '1998-06-06', '243', '1998-06-06', '1', 'EXISTS'),
       ('NEW', '1998-06-06', '21', '1998-06-06', '1', 'EXISTS'),
       ('NEW', '1998-06-06', '253', '1998-06-06', '1', 'EXISTS'),
       ('NEW', '1998-06-06', '273', '1998-06-06', '3', 'EXISTS'),
       ('NEW', '1998-06-06', '238', '1998-06-06', '4', 'EXISTS');

INSERT INTO books (title, pages, image_path, delete_status)
VALUES ('War and peace', '1365', 'image path', 'EXISTS'),
       ('The Master and Margarita', '638', 'image path', 'EXISTS'),
       ('Idiot', '496', 'image path', 'EXISTS'),
       ('The old man and the sea', '153', 'image path', 'EXISTS'),
       ('Eugene Onegin', '462', 'image path', 'EXISTS');

INSERT INTO book_copies (book_copy_status, registration_date, image_path, price_per_day, book_id, delete_status)
VALUES ('AVAILABLE', '2019-03-01', 'image path', '150', 1, 'EXISTS'),
       ('AVAILABLE', '2020-06-01', 'image path', '210', 2, 'EXISTS'),
       ('AVAILABLE', '2021-08-04', 'image path', '225', 2, 'EXISTS'),
       ('AVAILABLE', '2017-10-10', 'image path', '128', 5, 'EXISTS'),
       ('AVAILABLE', '2020-06-02', 'image path', '311', 3, 'EXISTS');

INSERT INTO order_book_copy_links (order_id, book_copy_id)
VALUES (1, 3),
       (1, 2),
       (3, 4),
       (4, 1),
       (5, 5);

INSERT INTO genres (genre_name, delete_status)
VALUES ('NOVEL', 'EXISTS'),
       ('ADVENTURE', 'EXISTS'),
       ('COMEDY', 'EXISTS'),
       ('CRIME', 'EXISTS'),
       ('HORROR', 'EXISTS'),
       ('SCIENCE FICTION', 'EXISTS'),
       ('ROMANCE', 'EXISTS');

INSERT INTO book_genre_links (book_id, genre_id)
VALUES (1, 3),
       (2, 1),
       (3, 1),
       (4, 4),
       (5, 2);

INSERT INTO authors (first_name, last_name, birth_date, image_path, delete_status)
VALUES ('Lev', 'Tolstoy', '1879-04-04', 'image path', 'EXISTS'),
       ('Ernest', 'Hemingway', '1903-07-07', 'image path', 'EXISTS'),
       ('Mikhail', 'Bulgakov', '1885-10-10', 'image path', 'EXISTS'),
       ('Alexander', 'Pushkin', '1852-02-02', 'image path', 'EXISTS'),
       ('Fedor', 'Dostoevsky', '1845-01-01', 'image path', 'EXISTS');

INSERT INTO book_author_links (book_id, author_id)
VALUES (1, 1),
       (2, 3),
       (3, 5),
       (4, 2),
       (5, 4);

INSERT INTO book_damage (image_path, damage_description, user_id, order_id, book_copy_id, delete_status)
VALUES ('image path', 'damage1', '1', '1', '3', 'EXISTS'),
       ('image path', 'damage2', '1', '1', '2', 'EXISTS'),
       ('image path', 'damage3', '3', '4', '1', 'EXISTS'),
       ('image path', 'damage4', '4', '5', '5', 'EXISTS');