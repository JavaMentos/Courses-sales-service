CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       name VARCHAR(100) NOT NULL,
                       phone_number VARCHAR(20),
                       date_of_birth DATE,
                       role_id BIGINT REFERENCES roles(id)
);

CREATE TABLE roles (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(50) NOT NULL
);

CREATE TABLE courses (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         description TEXT,
                         price BIGINT NOT NULL,
                         status VARCHAR(50) NOT NULL CHECK (status IN ('AVAILABLE', 'IN_CREATION', 'DELETED')),
                         created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE videos (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        path TEXT NOT NULL,
                        course_id BIGINT REFERENCES courses(id),
                        description TEXT
);

CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,
                        user_id BIGINT REFERENCES users(id),
                        course_id BIGINT REFERENCES courses(id),
                        order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        status VARCHAR(50)
);

CREATE TABLE user_courses (
                              user_id BIGINT REFERENCES users(id),
                              course_id BIGINT REFERENCES courses(id),
                              PRIMARY KEY (user_id, course_id)
);
