package com.takeoff.magic_cafe_rider.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Owner {
    @Id
    @Column(nullable = false, unique=true)
    private String uid;

    @Column(nullable = false)
    private Long qid;

    @Column(nullable = false)
    private String word;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Column(nullable = false, updatable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private boolean deleted;
}
