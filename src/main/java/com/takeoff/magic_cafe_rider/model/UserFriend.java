package com.takeoff.magic_cafe_rider.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.takeoff.magic_cafe_rider.enums.FriendState;
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
public class UserFriend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique=true)
    private Long idx;

    @Column(nullable = false)
    private String uid;

    @Column(nullable = false)
    private String fid;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FriendState state;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Column(nullable = false, updatable = false)
    private LocalDateTime date;
}
