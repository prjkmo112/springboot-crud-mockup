package com.momo.sparta.mainapi.domains.order.dto;

import com.momo.sparta.commonmysqldb.dto.BaseDtEntityDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@FieldNameConstants
public class OrderDto extends BaseDtEntityDto {

    private String orderKey;

    private String productKey;

    private String productName;

}
