package com.takeoff.magic_cafe_rider.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.takeoff.magic_cafe_rider.enums.ServiceType;
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
public class User {
    @Id
    @Column(nullable = false, unique=true)
    private String uid;

    @Column()
    private int role; //0: normal, 1: admin, 2: cafe-owner

    @Column()
    @Enumerated(EnumType.STRING)
    private ServiceType service;

    @Column()
    private boolean serviceCodec;

    @Column()
    private String nick;

    @Column(nullable = false)
    private String email;

    @Column()
    private String avatar;

    @Column()
    private String roadSignUrl;

    @Column()
    private String roadSignFile;

    @Column()
    private String youtube;

    @Column()
    private String instagram;

    @Column(nullable = false)
    private int bikeBrand;

    @Column(nullable = false)
    private int bikeModel;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private int guildPoint;

    @Column(nullable = false)
    private Long agit; //qid

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Column(nullable = false)
    private LocalDateTime lastLoginTime;
}
