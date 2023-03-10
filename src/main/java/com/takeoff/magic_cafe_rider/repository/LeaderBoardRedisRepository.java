package com.takeoff.magic_cafe_rider.repository;

import com.takeoff.magic_cafe_rider.model.Ranker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Repository
public class LeaderBoardRedisRepository {
    public static final String KEY = "magic_cafe_rider:leaderBoard";
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private ZSetOperations<String, String> zSetOps;

    @PostConstruct
    public void init() {
        zSetOps = redisTemplate.opsForZSet();
    }

    public List<String> getPlayersRankOfRange(int startIndex, int endIndex) {
        Set<String> rankReverseSet = zSetOps.reverseRange(KEY, startIndex, endIndex);
        Iterator<String> iter = rankReverseSet.iterator();
        List<String> list = new ArrayList<>(rankReverseSet.size());

        while (iter.hasNext()) {
            list.add(iter.next());
        }

        return list;
    }

    public Ranker getOnePlayerRank(String uid) {
        Long playerRank = zSetOps.reverseRank(KEY, uid);

        Ranker ranker = new Ranker();
        ranker.setUid(uid);

        if (playerRank == null) {
            ranker.setRank(0L);
            ranker.setScore(0D);
        }
        else {
            ranker.setRank(playerRank + 1);
            ranker.setScore(zSetOps.score(KEY, uid));
        }

        return ranker;
    }

    public void updateLeaderBoard(String uid, double score) {
        zSetOps.add(KEY, uid, score);
    }
}
