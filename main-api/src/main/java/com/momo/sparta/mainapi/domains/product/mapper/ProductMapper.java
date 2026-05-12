package com.momo.sparta.mainapi.domains.product.mapper;

import com.momo.sparta.commonmysqldb.entity.Product;
import com.momo.sparta.mainapi.domains.product.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDto toDto(Product product);

    Product fromDto(ProductDto productDto);
    
}
