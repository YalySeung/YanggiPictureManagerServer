CREATE TABLE users (
                       user_id        BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username       VARCHAR(50) NOT NULL UNIQUE,
                       password       VARCHAR(255) NOT NULL,
                       display_name   VARCHAR(100),
                       role           VARCHAR(20) DEFAULT 'USER',
                       created_at     DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE photos (
                        photo_id       BIGINT AUTO_INCREMENT PRIMARY KEY,
                        user_id        BIGINT NOT NULL,
                        filename       VARCHAR(255) NOT NULL,
                        original_name  VARCHAR(255),
                        file_type      VARCHAR(20),
                        file_size      BIGINT,
                        tag            VARCHAR(100),
                        uploaded_at    DATETIME DEFAULT CURRENT_TIMESTAMP,
                        deleted        BOOLEAN DEFAULT FALSE,

                        CONSTRAINT fk_photos_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE favorites (
                           favorite_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
                           user_id        BIGINT NOT NULL,
                           photo_id       BIGINT NOT NULL,
                           favorited_at   DATETIME DEFAULT CURRENT_TIMESTAMP,

                           UNIQUE (user_id, photo_id),
                           CONSTRAINT fk_fav_user FOREIGN KEY (user_id) REFERENCES users(user_id),
                           CONSTRAINT fk_fav_photo FOREIGN KEY (photo_id) REFERENCES photos(photo_id)
);

CREATE TABLE photo_logs (
                            log_id         BIGINT AUTO_INCREMENT PRIMARY KEY,
                            photo_id       BIGINT NOT NULL,
                            user_id        BIGINT,
                            action         VARCHAR(50),  -- ex: "UPLOAD", "DOWNLOAD", "DELETE", "FAVORITE"
                            timestamp      DATETIME DEFAULT CURRENT_TIMESTAMP,
                            detail         TEXT,

                            CONSTRAINT fk_log_photo FOREIGN KEY (photo_id) REFERENCES photos(photo_id),
                            CONSTRAINT fk_log_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE photo_tags (
                            tag_id         BIGINT AUTO_INCREMENT PRIMARY KEY,
                            photo_id       BIGINT NOT NULL,
                            tag            VARCHAR(100),

                            CONSTRAINT fk_tag_photo FOREIGN KEY (photo_id) REFERENCES photos(photo_id)
);
