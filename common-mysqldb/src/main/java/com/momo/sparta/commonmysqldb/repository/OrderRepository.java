package com.momo.sparta.commonmysqldb.repository;

import com.momo.sparta.commonmysqldb.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    Optional<Order> findByOrderKey(String orderKey);

    @Query(value = "SELECT o FROM Order o JOIN FETCH o.product",
            countQuery = "SELECT count(o) FROM Order o")
    Page<Order> findAllWithProduct(Pageable pageable);

    @Query(value = "SELECT o FROM Order o JOIN FETCH o.product WHERE o.orderKey = :orderKey")
    Optional<Order> findByOrderKeyWithProduct(@Param("orderKey") String orderKey);
}