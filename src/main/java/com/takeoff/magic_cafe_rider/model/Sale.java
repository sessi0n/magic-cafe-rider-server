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
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long sid;

    @Column(nullable = false)
    private String uid;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String imgUrl;

    @Column(nullable = false)
    private LocalDateTime start;

    @Column(nullable = false)
    private LocalDateTime end;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Column(nullable = false, updatable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private boolean deleted;
}
