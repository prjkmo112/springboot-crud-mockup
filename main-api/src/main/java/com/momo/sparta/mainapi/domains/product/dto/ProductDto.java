package com.momo.sparta.mainapi.domains.product.dto;

import com.momo.sparta.commonmysqldb.dto.BaseDtEntityDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@FieldNameConstants
public class ProductDto extends BaseDtEntityDto {

    private String productKey;

    private String name;

    private Integer price;

}
