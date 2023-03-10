package com.takeoff.magic_cafe_rider.protocol;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class SaleListReq {
    @NotNull
    private int pageNum;
    @NotNull
    private int pageSize;
}
