CREATE TABLE users
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255)                                                   NOT NULL,
    email       VARCHAR(128)                                                   NOT NULL,
    passwd      VARCHAR(255) CHARACTER SET ascii collate ascii_bin             NOT NULL,
    role        VARCHAR(64)                                                    NOT NULL,
    created_at  datetime default CURRENT_TIMESTAMP                             not null,
    modified_at datetime default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP null,

    constraint uk_users_email
        unique (email)
);

