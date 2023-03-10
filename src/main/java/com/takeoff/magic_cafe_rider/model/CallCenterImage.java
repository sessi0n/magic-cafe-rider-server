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
public class CallCenterImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique=true)
    private Long idx;

    @Column(nullable = false)
    private Long csid;

    @Column(nullable = false)
    private String imgUrl;

    @Column(nullable = false)
    private String imgFile;
}
