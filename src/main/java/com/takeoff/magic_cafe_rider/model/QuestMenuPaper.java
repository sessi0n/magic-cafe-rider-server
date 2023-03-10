package com.takeoff.magic_cafe_rider.model;

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
public class QuestMenuPaper {
    @Id
    @Column(nullable = false)
    private Long qid;

    @Column(nullable = false)
    private String imgUrl;

    @Column(nullable = false)
    private String imgFile;

    @Column(nullable = false)
    private boolean deleted;
}
