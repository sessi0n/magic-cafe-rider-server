package com.takeoff.magic_cafe_rider.protocol;

import com.takeoff.magic_cafe_rider.enums.FootType;
import com.takeoff.magic_cafe_rider.model.Quest;
import com.takeoff.magic_cafe_rider.model.UserFootPrint;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class GetFootPrint {
    @NotNull
    List<FootPrintLog> footPrintLogs;

    @Data
    public static class FootPrintLog {
        Long qid;
        String name;
        FootType type;
        LocalDateTime date;
        boolean can;
    }
}
