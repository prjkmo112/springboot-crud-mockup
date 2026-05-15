create table `orders`
(
    id          bigint auto_increment                                          not null
        primary key,
    order_key   varchar(64)                                                    not null,
    product_key varchar(64)                                                    not null,
    created_at  datetime default CURRENT_TIMESTAMP                             not null,
    modified_at datetime default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP null,

    constraint uk_orders_product_key
        unique (order_key)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;