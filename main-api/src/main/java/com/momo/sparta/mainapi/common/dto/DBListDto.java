package com.momo.sparta.mainapi.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DBListDto<T> {
    private T data;

    private Long total;
    private Integer page;
    private Integer pageSize;
    private Integer totalPage;
}
