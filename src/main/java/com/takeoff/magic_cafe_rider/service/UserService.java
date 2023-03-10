package com.takeoff.magic_cafe_rider.service;

import com.takeoff.magic_cafe_rider.enums.FootType;
import com.takeoff.magic_cafe_rider.enums.ServiceType;
import com.takeoff.magic_cafe_rider.model.*;
import com.takeoff.magic_cafe_rider.repository.*;
import com.takeoff.magic_cafe_rider.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserFavoriteBikersRepository userFavoriteBikersRepository;
    private final UserQuestRepository userQuestRepository;
    private final UserRegisterQuestRepository userRegisterQuestRepository;
    private final UserRegisterNpcRepository userRegisterNpcRepository;
    private final QuestRepository questRepository;
    private final UserBlockRepository userBlockRepository;
    private final UserFootPrintRepository userFootPrintRepository;
    private final OwnerRepository ownerRepository;
    private final UserFriendsRepository userFriendsRepository;

    @Transactional
    public User newUserCreate(String uid, String email, String nick, String avatar, ServiceType service) {
        User user = User.builder()
                .uid(uid)
                .service(service)
                .email(email)
                .nick(nick)
                .avatar(avatar)
                .roadSignUrl("")
                .roadSignFile("")
                .score(0)
                .agit(0L)
                .build();

        userRepository.save(user);
        return user;
    }

    public User getUserData(String uid) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUid(uid));
        return user.orElse(null);
    }

    public List<User> getUserDataByEmail(String email) {
        return userRepository.findAllByEmail(email);
    }

    public List<User> getFavoriteBikers(String uid) {
        List<UserFavoriteBiker> bikers = userFavoriteBikersRepository.findAllByUid(uid);

        List<String> uids = bikers.stream().map(UserFavoriteBiker::getBiker).collect(Collectors.toList());

        return userRepository.findByUidIn(uids);
    }

    public List<UserQuest> getUserQuestList(String uid) {
        return userQuestRepository.findAllByUidOrderByIdxDesc(uid);
    }

    public List<UserQuest> getUserCompletedQuestList(String uid) {
        return userQuestRepository.findAllByUidAndCompletedIsTrueOrderByIdxDesc(uid);
    }

    public List<UserRegisterQuest> getUserRegisterQuests(String uid) {
        return userRegisterQuestRepository.findAllByUidOrderByIdxDesc(uid);
    }

    public List<Quest> getUserRegisterQuests2(String uid) {
//        return userRegisterQuestRepository.findAllByUidOrderByIdxDesc(uid);
        return questRepository.findAllUserRegister(uid);
    }

    public UserRegisterQuest getUserReigsterQuest(Long qIdx) {
        return userRegisterQuestRepository.findByIdx(qIdx);
    }

    public void delUserQuest(UserQuest userQuest) {
        userQuestRepository.delete(userQuest);
    }


    public void addUserRegisterQuest(String uid, Long qid) {
        UserRegisterQuest userRegisterQuest = UserRegisterQuest.builder()
                .uid(uid)
                .qid(qid)
                .build();
        userRegisterQuestRepository.save(userRegisterQuest);
    }

    public List<UserFavoriteBiker> getUserFavoriteBiker(String uid) {
        return userFavoriteBikersRepository.findAllByUid(uid);
    }

    public void delUserFavoriteBiker(UserFavoriteBiker temp) {
        userFavoriteBikersRepository.delete(temp);
    }

    public void saveUserFavoriteBikerRepository(UserFavoriteBiker userFavoriteBiker) {
        userFavoriteBikersRepository.save(userFavoriteBiker);
    }

    public List<User> getUserList(List<String> uids) {
        return userRepository.findByUidIn(uids);
    }

    public UserQuest addUserQuest(String uid, Long qid) {
        UserQuest userQuest = UserQuest.builder()
                .uid(uid)
                .qid(qid)
                .completed(false)
                .build();
        userQuestRepository.save(userQuest);
        return userQuest;
    }

    public int completeQuest(Long qid, String uid) {
        Quest quest = questRepository.findByQid(qid);

        User writer = userRepository.findByUid(quest.getUid());
        writer.setGuildPoint(writer.getGuildPoint() + Constants.COMPLETED_QUEST_GUILD_POINT);
        userRepository.save(writer);

        quest.setCompleteCount(quest.getCompleteCount()+1);
        questRepository.save(quest);

        User completer = userRepository.findByUid(uid);
        int userScore = completer.getScore() + Constants.COMPLETED_QUEST_SCORE;
        completer.setScore(userScore);
        userRepository.save(completer);

        UserQuest userQuest = UserQuest.builder()
                .completed(true)
                .qid(qid)
                .completedDate(LocalDateTime.now())
                .uid(uid)
                .build();

//        userQuest.setCompletedDate(LocalDateTime.now());
//        userQuest.setCompleted(true);
        userQuestRepository.save(userQuest);

        return userScore;
    }
    public int completeQuest(UserQuest userQuest, String uid) {
        Quest quest = questRepository.findByQid(userQuest.getQid());

        User writer = userRepository.findByUid(quest.getUid());
        writer.setGuildPoint(writer.getGuildPoint() + Constants.COMPLETED_QUEST_GUILD_POINT);
        userRepository.save(writer);

        quest.setAcceptCount(quest.getAcceptCount()-1);
        quest.setCompleteCount(quest.getCompleteCount()+1);
        questRepository.save(quest);

        User completer = userRepository.findByUid(uid);
        int userScore = completer.getScore() + Constants.COMPLETED_QUEST_SCORE;
        completer.setScore(userScore);
        userRepository.save(completer);

        userQuest.setCompletedDate(LocalDateTime.now());
        userQuest.setCompleted(true);
        userQuestRepository.save(userQuest);

        return userScore;
    }

    public void delUserRegisterQuest(UserRegisterQuest userRegisterQuest) {
        userRegisterQuestRepository.delete(userRegisterQuest);
    }

    public List<UserRegisterNpc> getUserRegisterNpcs(String uid) {
        return userRegisterNpcRepository.findAllByUid(uid);
    }

    public UserRegisterNpc getUserRegisterNpc(Long nid) {
        return userRegisterNpcRepository.findByIdx(nid);
    }

    public void update(User user) {
        userRepository.save(user);
    }

    public void update(Owner owner) {
        ownerRepository.save(owner);
    }


    public List<User> getRankerList() {
        return userRepository.findTop20ByOrderByScoreDesc();
    }

    public void addUserBlock(UserBlock userBlock) {
        userBlockRepository.save(userBlock);
    }

    public List<UserBlock> getUserBlockList(String uid) {
        return userBlockRepository.findAllByUid(uid);
    }

    public void delUserBlock(UserBlock userBlock) {
        userBlockRepository.delete(userBlock);
    }


    public void remove(User user) {
        userQuestRepository.deleteAllByUid(user.getUid());
        userFootPrintRepository.deleteAllByUid(user.getUid());

        userRepository.delete(user);
    }

    public List<UserFootPrint> getUserFootList(String uid) {
        return userFootPrintRepository.findAllByUidOrderByIdxDesc(uid);
    }

//    public List<UserFootPrint> getUserFootDistinctList(String uid) {
//        return userFootPrintRepository.findDistinctCustom(uid);
//    }

    public UserQuest getUserQuest(String uid, Long qid) {
        return userQuestRepository.findByUidAndQid(uid, qid);
    }

    public UserFootPrint getUserFootLast(String uid, Long qid) {
        return userFootPrintRepository.findTopByUidAndQidOrderByDateDesc(uid, qid).orElse(null);
    }

    public void addUserFootPrint(String uid, Long qid, FootType type) {
        UserFootPrint userFootPrint = UserFootPrint.builder()
                .qid(qid)
                .uid(uid)
                .type(type)
                .build();

        userFootPrintRepository.save(userFootPrint);
    }

    public Owner setOwner(User target, Long qid) {
        Owner owner = Owner.builder()
                .uid(target.getUid())
                .qid(qid)
                .word("")
                .deleted(false)
                .build();

        target.setRole(2);
        userRepository.save(target);

        return ownerRepository.save(owner);
    }

    public Owner getOwner(String uid) {
        return ownerRepository.findByUidAndDeletedIsFalse(uid);
    }

    public Owner getOwner(Long qid) {
        return ownerRepository.findByQidAndDeletedIsFalse(qid);
    }

    public List<UserFriend> getUserFriends(String uid) {
        return userFriendsRepository.findAllByUidOrderByDateDesc(uid);
    }
    public UserFriend getUserFriend(String uid, String fid) {
        return userFriendsRepository.findByUidAndFid(uid, fid).orElse(null);
    }

    public void saveUserFriend(UserFriend userFriend) {
        userFriendsRepository.save(userFriend);
    }

    public void saveUserFriends(List<UserFriend> userFriends) {
        userFriendsRepository.saveAll(userFriends);
    }

    public void removeUserFriend(UserFriend userFriend) {
        userFriendsRepository.delete(userFriend);
    }
}
