package com.takeoff.magic_cafe_rider.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.takeoff.magic_cafe_rider.model.Owner;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper=false)
public class OwnerWordRes {
    @NotNull
    Owner owner;

}
