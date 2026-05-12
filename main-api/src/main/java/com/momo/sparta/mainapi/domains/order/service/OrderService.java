package com.momo.sparta.mainapi.domains.order.service;

import com.momo.sparta.commonmysqldb.entity.Order;
import com.momo.sparta.commonmysqldb.entity.Product;
import com.momo.sparta.commonmysqldb.repository.OrderRepository;
import com.momo.sparta.commonmysqldb.repository.ProductRepository;
import com.momo.sparta.mainapi.common.dto.DBListDto;
import com.momo.sparta.mainapi.domains.order.dto.OrderDto;
import com.momo.sparta.mainapi.domains.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    private Order getOrderEntityByOrderKey(String orderKey) {
        return orderRepository.findByOrderKey(orderKey)
                .orElseThrow(() -> {
                    log.warn("order not found (orderKey: {})", orderKey);
                    return new IllegalArgumentException("order not found");
                });
    }

    @Transactional(readOnly = true)
    public OrderDto getOrderItem(String orderKey) {
        Order order = getOrderEntityByOrderKey(orderKey);
        return OrderMapper.INSTANCE.toDto(order);
    }

    @Transactional(readOnly = true)
    public DBListDto<List<OrderDto>> getOrderList(Pageable pageable) {
        Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        Page<Order> page = orderRepository.findAllWithProduct(pageRequest);

        return DBListDto.<List<OrderDto>>builder()
                .data(page.map(OrderMapper.INSTANCE::toDto).toList())
                .total(page.getTotalElements())
                .totalPage(page.getTotalPages())
                .page(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .build();
    }

    public OrderDto createOrder(OrderDto orderDto) {
        Product product = productRepository.findByProductKey(orderDto.getProductKey())
                .orElseThrow(() -> {
                    log.warn("product not found (productKey: {})", orderDto.getProductKey());
                    return new IllegalArgumentException("product not found");
                });

        Order order = OrderMapper.INSTANCE.fromDto(orderDto);
        order.setId(null);
        order.setCreatedAt(LocalDateTime.now());
        order.setModifiedAt(null);

        Order savedOrder = orderRepository.save(order);
        savedOrder.setProduct(product);
        return OrderMapper.INSTANCE.toDto(savedOrder);
    }

    @Transactional
    public OrderDto updateOrder(OrderDto orderDto) {
        Product product = productRepository.findByProductKey(orderDto.getProductKey())
                .orElseThrow(() -> {
                    log.warn("product not found (productKey: {})", orderDto.getProductKey());
                    return new IllegalArgumentException("product not found");
                });

        Order order = getOrderEntityByOrderKey(orderDto.getOrderKey());
        order.setProductKey(orderDto.getProductKey());
        order.setProduct(product);
        order.setModifiedAt(LocalDateTime.now());

        return OrderMapper.INSTANCE.toDto(order);
    }

    @Transactional
    public void deleteOrder(String orderKey) {
        Order order = getOrderEntityByOrderKey(orderKey);
        orderRepository.delete(order);
    }

}
