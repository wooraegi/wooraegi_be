package com.project.teamttt.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BABY_LOG")
@Builder
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BabyLog extends BaseEntity{

    /**
     * 반려동물 별 로그 아이디
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long babyLogId;

    /**
     * babys테이블에 babyId 컬럼을 참조
     */
    @ManyToOne
    @JoinColumn(name = "babyId", referencedColumnName = "babyId")
    private Baby baby;

}
