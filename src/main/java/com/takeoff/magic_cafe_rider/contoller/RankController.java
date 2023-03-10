package com.takeoff.magic_cafe_rider.contoller;

import com.takeoff.magic_cafe_rider.model.Ranker;
import com.takeoff.magic_cafe_rider.model.User;
import com.takeoff.magic_cafe_rider.protocol.RankingReq;
import com.takeoff.magic_cafe_rider.protocol.RankingRes;
import com.takeoff.magic_cafe_rider.repository.LeaderBoardRedisRepository;
import com.takeoff.magic_cafe_rider.service.RankService;
import com.takeoff.magic_cafe_rider.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/rank")
public class RankController {
    private final RankService rankService;
    private final UserService userService;

    @PostMapping(value = "/leader_board")
    public RankingRes function(@RequestBody @Valid RankingReq req, RankingRes res) {

//        List<String> uids = rankService.getPlayerRankerOfRange(req.getPageNum(), req.getPageSize());
        List<User> users = rankService.getPlayerRankerOfRange();

//        List<User> users = userService.getUserList(uids);

        List<RankingRes.Ranker> rankers = new ArrayList<>();
        long rank = (long) req.getPageNum() *req.getPageSize() +1;

        for (User user : users) {
            RankingRes.Ranker ranker = new RankingRes.Ranker();
            ranker.setRank(rank++);
            ranker.setUser(user);
            rankers.add(ranker);
        }

        res.setRankers(rankers);
        return res;
    }
}
