package com.takeoff.magic_cafe_rider.protocol;

import com.takeoff.magic_cafe_rider.model.Review;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotNull;
import java.util.List;
@Data
@EqualsAndHashCode(callSuper=false)
public class GetReview {
    @NotNull
    List<Review> reviews;
}
