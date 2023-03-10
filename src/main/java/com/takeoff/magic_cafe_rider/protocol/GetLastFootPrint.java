package com.takeoff.magic_cafe_rider.protocol;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class GetLastFootPrint {
    @NotNull
    List<GetFootPrint.FootPrintLog> footPrintLogs;
}
