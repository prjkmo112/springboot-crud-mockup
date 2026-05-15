package com.momo.sparta.mainapi.domains.order.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@FieldNameConstants
public class CreateOrderDto {

    private String orderKey;

    private String productKey;

}
