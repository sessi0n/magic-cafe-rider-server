package com.takeoff.magic_cafe_rider.protocol;

import com.takeoff.magic_cafe_rider.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class FavoriteRes {
    @NotNull
    Integer retCode; //1 : add Success 2: delete Success

    @NotNull
    List<User> favoriteBikers;
}
