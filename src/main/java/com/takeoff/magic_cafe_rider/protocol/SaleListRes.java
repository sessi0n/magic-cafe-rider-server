package com.takeoff.magic_cafe_rider.protocol;

import com.takeoff.magic_cafe_rider.model.Sale;
import com.takeoff.magic_cafe_rider.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class SaleListRes {
    @Data
    public static class SaleInfo {
        Sale sale;
        User user;
    }

    @NotNull
    List<SaleInfo> saleInfos;
}
