-- роли
INSERT INTO roles (id, name)
VALUES (1, 'ADMIN'),
       (2, 'USER');

-- пользователи
INSERT INTO users (id, email, password, name, phone_number, date_of_birth, role_id)
VALUES (1, 'admin@example.com', '$2a$12$.pox.xlfa4DKcQM6EaLOSeQMXCaOGnXiTZATSc39iMM0FtBd5pAdG', 'Administrator',
        '89991234567', '1981-07-15', 1),
       (2, 'testUser@example.com', '$2a$12$PRb9kAFNBzdsdxbaa7RPxebRDFhgANcEg2DG/e2RyOmUxfr4G9.cC', 'Test user',
        '87779876543', '1997-05-10', 2);

-- курсы
INSERT INTO courses (id, name, description, price, status)
VALUES
    (1, 'Pilates Basics', 'Курс по pilates для начинающих.', 1500, 'AVAILABLE'),
    (2, 'Advanced Pilates', 'Курс по pilates для продвинутых.', 3000, 'IN_CREATION');

-- видео
INSERT INTO videos (id, description, name, path, course_id)
VALUES (1, 'this first video for training at pilates', 'Intro to Pilates', 'courses/pilates/intro-pilates.mp4', 1),
       (2, 'this latest video for finishing course at pilates', 'Pilates Warm-Up', 'courses/pilates/warm-up.mp4', 1),
       (3, 'this hard continue training skills in pilates', 'Advanced Techniques',
        'courses/pilates/advanced-techniques.mp4', 2);

-- заказы
INSERT INTO orders (id, user_id, course_id, order_date, status)
VALUES (1, 2, 1, CURRENT_TIMESTAMP, 'COMPLETED');
