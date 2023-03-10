package com.takeoff.magic_cafe_rider.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class QuestThumbnail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique=true)
    private Long thumbId;

    @Column(nullable = false)
    private String file;

    @Column(nullable = false)
    private String url;
}
