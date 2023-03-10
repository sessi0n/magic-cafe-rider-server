package com.takeoff.magic_cafe_rider.protocol;

import com.takeoff.magic_cafe_rider.model.Review;
import com.takeoff.magic_cafe_rider.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class GetReviewList {
    @NotNull
    List<ReviewInfo> reviewInfos;
    @Data
    public static class ReviewInfo {
        Review review;
        User user;
    }
}
