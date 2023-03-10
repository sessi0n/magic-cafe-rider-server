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
public class Conf {
    @Id
    @Column(nullable = false)
    private String key;

    @Column(nullable = false)
    private String value;
}
