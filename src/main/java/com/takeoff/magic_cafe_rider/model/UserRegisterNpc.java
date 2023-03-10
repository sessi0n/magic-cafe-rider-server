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
public class UserRegisterNpc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long idx;

    @Column(nullable = false)
    private String uid;

    @Column(nullable = false)
    private Long nid;

}
