create table products
(
    id          bigint auto_increment
        primary key,
    product_key varchar(64)                                                    not null,
    name        varchar(200)                                                   not null,
    price       int                                                            not null,
    created_at  datetime default CURRENT_TIMESTAMP                             not null,
    modified_at datetime default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP null,

    constraint products_pk
        unique (product_key)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;