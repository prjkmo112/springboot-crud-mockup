package com.momo.sparta.mainapi.domains.order.controller;

import com.momo.sparta.mainapi.common.dto.DBListDto;
import com.momo.sparta.mainapi.domains.order.dto.OrderDto;
import com.momo.sparta.mainapi.domains.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/item")
    public OrderDto getOrderItem(@RequestParam String orderKey) {
        return orderService.getOrderItem(orderKey);
    }

    @GetMapping("/list")
    public DBListDto<List<OrderDto>> getOrderList(@PageableDefault(size = 10) Pageable pageable) {
        return orderService.getOrderList(pageable);
    }

    @PostMapping("/create")
    public OrderDto createOrder(@RequestBody @Valid OrderDto orderDto) {
        return orderService.createOrder(orderDto);
    }

    @PutMapping("/update")
    public OrderDto updateOrder(@RequestBody @Valid OrderDto orderDto) {
        return orderService.updateOrder(orderDto);
    }

    @DeleteMapping("/delete")
    public void deleteOrder(@RequestParam String orderKey) {
        orderService.deleteOrder(orderKey);
    }

}
