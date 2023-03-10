package com.takeoff.magic_cafe_rider.service;

import com.takeoff.magic_cafe_rider.model.Ranker;
import com.takeoff.magic_cafe_rider.model.User;
import com.takeoff.magic_cafe_rider.repository.LeaderBoardRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RankService {
    private final LeaderBoardRedisRepository leaderBoardRedisRepository;
    private final UserService userService;

    public List<String> getPlayerRankerOfRange(int pageNum, int pageSize) {
        return leaderBoardRedisRepository.getPlayersRankOfRange(pageNum * pageSize, (pageNum+1)*pageSize);
    }

    public List<User> getPlayerRankerOfRange() {
        return userService.getRankerList();
    }

    public Ranker getOnePlayerRank(String uid) {
        return leaderBoardRedisRepository.getOnePlayerRank(uid);
    }

    public void updateOnePlayerRank(String uid, double score) {
        leaderBoardRedisRepository.updateLeaderBoard(uid, score);
    }
}
