package com.takeoff.magic_cafe_rider.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.takeoff.magic_cafe_rider.enums.QuestType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Quest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique=true)
    private Long qid;

    @Column(nullable = false)
    private String uid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column()
    private Integer area;
    @Column()
    private Integer city;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private QuestType type;

    @Column(nullable = false)
    private Double lat;
    @Column(nullable = false)
    private Double lng;

    @Column(nullable = false)
    private Integer acceptCount;
    @Column(nullable = false)
    private Integer completeCount;
    @Column(nullable = false)
    private Integer level; //0 easy ~ 5 hard

    @Column()
    private String youtubeUrl;

    @Column()
    private String instagram;

    @Column()
    private String thumbnail;

    @Column()
    private String pictureHelp;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @Column(nullable = false)
    private boolean deleted;

    @Column(nullable = false)
    private Long thumbId;
}
