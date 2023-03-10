package com.takeoff.magic_cafe_rider.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.takeoff.magic_cafe_rider.enums.NpcType;
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
public class Npc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique=true)
    private Long nid;

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
    private NpcType type;

    @Column(nullable = false)
    private Double lat;
    @Column(nullable = false)
    private Double lng;

    @Column()
    private String youtubeUrl;

    @Column()
    private String instagram;

    @Column()
    private String webUrl;

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
}
