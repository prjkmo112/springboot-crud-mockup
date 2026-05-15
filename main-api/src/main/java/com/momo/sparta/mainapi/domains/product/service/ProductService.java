package com.momo.sparta.mainapi.domains.product.service;

import com.momo.sparta.commonmysqldb.entity.Product;
import com.momo.sparta.commonmysqldb.repository.ProductRepository;
import com.momo.sparta.mainapi.common.dto.DBListDto;
import com.momo.sparta.mainapi.domains.product.dto.ProductDto;
import com.momo.sparta.mainapi.domains.product.mapper.ProductMapper;
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
public class ProductService {

    private final ProductRepository productRepository;

    private Product getProductEntityByProductKey(String productKey) {
        return productRepository.findByProductKey(productKey)
                .orElseThrow(() -> {
                    log.warn("product not found (productKey: {})", productKey);
                    return new IllegalArgumentException("product not found");
                });
    }

    public ProductDto getProductItem(String productKey) {
        Product product = getProductEntityByProductKey(productKey);
        return ProductMapper.INSTANCE.toDto(product);
    }

    public DBListDto<List<ProductDto>> getProductList(Pageable pageable) {
        Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        Page<Product> page = productRepository.findAll(pageRequest);

        return DBListDto.<List<ProductDto>>builder()
                .data(page.map(ProductMapper.INSTANCE::toDto).toList())
                .total(page.getTotalElements())
                .totalPage(page.getTotalPages())
                .page(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .build();
    }

    public ProductDto createProduct(ProductDto productDto) {
        Product product = ProductMapper.INSTANCE.fromDto(productDto);
        product.setId(null);
        product.setCreatedAt(LocalDateTime.now());
        product.setModifiedAt(null);

        Product savedProduct = productRepository.save(product);
        return ProductMapper.INSTANCE.toDto(savedProduct);
    }

    @Transactional
    public ProductDto updateProduct(ProductDto productDto) {
        Product product = getProductEntityByProductKey(productDto.getProductKey());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setModifiedAt(LocalDateTime.now());

        return ProductMapper.INSTANCE.toDto(product);
    }

    @Transactional
    public void deleteProduct(String productKey) {
        Product product = getProductEntityByProductKey(productKey);
        productRepository.delete(product);
    }
}
