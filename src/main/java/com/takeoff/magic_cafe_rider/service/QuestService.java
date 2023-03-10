package com.takeoff.magic_cafe_rider.service;

import com.takeoff.magic_cafe_rider.enums.QuestType;
import com.takeoff.magic_cafe_rider.enums.SortType;
import com.takeoff.magic_cafe_rider.model.*;
import com.takeoff.magic_cafe_rider.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class QuestService {
    private final QuestRepository questRepository;
    private final QuestImageRepository questImageRepository;
    private final QuestReportRepository questReportRepository;
    private final QuestThumbnailRepository questThumbnailRepository;
    private final QuestMenuPaperRepository questMenuPaperRepository;
    private final ReviewRepository reviewRepository;

    public List<Quest> getQuestList(int pageNum, int pageSize, List<String> blockList, SortType sortType) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        if (sortType == SortType.COMPLETE_COUNT) {
            return questRepository.findAllByDeletedIsFalseAndUidNotInOrderByCompleteCountDescCreatedTimeDesc(blockList, pageable);
        }

        return questRepository.findAllByDeletedIsFalseAndUidNotInOrderByCreatedTimeDesc(blockList, pageable);
    }
    public List<Quest> getQuestList(int pageNum, int pageSize, int area, List<String> blockList, SortType sortType) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        if (sortType == SortType.COMPLETE_COUNT) {
            return questRepository.findAllByAreaAndDeletedIsFalseAndUidNotInOrderByCompleteCountDescCreatedTimeDesc(area, blockList, pageable);
        }
        return questRepository.findAllByAreaAndDeletedIsFalseAndUidNotInOrderByCreatedTimeDesc(area, blockList, pageable);
    }
    public List<Quest> getQuestList(int pageNum, int pageSize, String name, List<String> blockList, SortType sortType) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        if (sortType == SortType.COMPLETE_COUNT) {
            return questRepository.findAllByNameContainingAndDeletedIsFalseAndUidNotInOrderByCompleteCountDescCreatedTimeDesc(name, blockList, pageable);
        }

        return questRepository.findAllByNameContainingAndDeletedIsFalseAndUidNotInOrderByCreatedTimeDesc(name, blockList, pageable);
    }
    public List<Quest> getQuestList(int pageNum, int pageSize, QuestType type, List<String> blockList, SortType sortType) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        if (sortType == SortType.COMPLETE_COUNT) {
            return questRepository.findAllByTypeAndDeletedIsFalseAndUidNotInOrderByCompleteCountDescCreatedTimeDesc(type, blockList, pageable);
        }

        return questRepository.findAllByTypeAndDeletedIsFalseAndUidNotInOrderByCreatedTimeDesc(type, blockList, pageable);
    }

    public List<Quest> getQuestList(List<Long> qids) {
        return questRepository.findAllByQidInAndDeletedIsFalseOrderByCompleteCountDescCreatedTimeDesc(qids);
    }

    public List<Quest> getQuestListNoOrder(List<Long> qids) {
        return questRepository.findAllByQidInAndDeletedIsFalse(qids);
    }

    public List<QuestImage> getQuestImageList(Long qid) {
        return questImageRepository.findAllByQidAndDeletedIsFalse(qid);
    }



    @Transactional
    public Quest addQuest(Quest quest) {
        return questRepository.saveAndFlush(quest);
    }

    public List<QuestImage> addQuestImages(List<QuestImage> questImages) {
        return questImageRepository.saveAll(questImages);
    }

    public Quest updateQuest(Quest quest) {
        return questRepository.save(quest);
    }

    public Quest getQuest(Long qid) {
        return questRepository.findByQid(qid);
    }

    public void saveAcceptCount(Quest quest, int i) {
        int count = quest.getAcceptCount() + i;
        if (count < 0)
            count = 0;

        quest.setAcceptCount(count);

        questRepository.save(quest);
    }


    public void setReportQuest(String uid, Long qid) {
        QuestReport report = QuestReport.builder()
                .idx(0L)
                .uid(uid)
                .qid(qid)
                .build();

        questReportRepository.save(report);
    }

//    public void deleteQuestImages(List<QuestImage> delImages) {
//        questImageRepository.deleteAll(delImages);
//    }
//
//    public void deleteQuestImages(List<Long> ids, Long qid) {
//        questImageRepository.deleteByQidAndIdxIn(qid, ids);
//    }

    public QuestImage updateQuestImage(QuestImage questImage) {
        return questImageRepository.save(questImage);
    }

    public QuestThumbnail saveThumb(QuestThumbnail thumb) {
        return questThumbnailRepository.saveAndFlush(thumb);
    }

    public List<Quest> getQuestRegister(String uid, boolean isCompleted) {

        return questRepository.findAllWithUserQuestRegister(uid, isCompleted);
    }

    public List<Quest> getQuestMarkerInfo() {
        return questRepository.findAllByDeletedIsFalse();
    }

    public List<Review> getReviews(long id) {
        return reviewRepository.findAllByQidAndDeletedIsFalse(id);
    }

    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review getReview(long rid) {
        return reviewRepository.findByRid(rid);
    }

    public void delReview(Review review) {
        reviewRepository.delete(review);
    }

    public List<Quest> getQuestAgits() {
        return questRepository.findAllByTypeAndDeletedIsFalseOrderByNameAsc(QuestType.CAFE);
    }

    public QuestMenuPaper getQuestMenuPaper(long qid) {
        return questMenuPaperRepository.findByQid(qid);
    }

    public QuestMenuPaper addQuestMenuPaper(QuestMenuPaper questMenuPaper) {
        return questMenuPaperRepository.save(questMenuPaper);
    }
}
