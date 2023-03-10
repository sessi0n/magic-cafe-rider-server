package com.takeoff.magic_cafe_rider.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserFavoriteBiker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long idx;

    @Column(nullable = false)
    private String uid;

    @Column(nullable = false)
    private String biker;
}
