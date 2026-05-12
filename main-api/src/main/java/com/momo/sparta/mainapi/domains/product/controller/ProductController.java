package com.momo.sparta.mainapi.domains.product.controller;

import com.momo.sparta.mainapi.common.dto.DBListDto;
import com.momo.sparta.mainapi.domains.product.dto.ProductDto;
import com.momo.sparta.mainapi.domains.product.service.ProductService;
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
@RequestMapping("api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/item")
    public ProductDto getProductItem(@RequestParam String productKey) {
        return productService.getProductItem(productKey);
    }

    @GetMapping("/list")
    public DBListDto<List<ProductDto>> getProductList(@PageableDefault(size = 10) Pageable pageable) {
        return productService.getProductList(pageable);
    }

    @PostMapping("/create")
    public ProductDto createProduct(@RequestBody @Valid ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @PutMapping("/update")
    public ProductDto updateProduct(@RequestBody @Valid ProductDto productDto) {
        return productService.updateProduct(productDto);
    }

    @DeleteMapping("/delete")
    public void deleteProduct(@RequestParam String productKey) {
        productService.deleteProduct(productKey);
    }

}
