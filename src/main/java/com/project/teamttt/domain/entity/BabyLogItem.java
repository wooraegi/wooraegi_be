package com.project.teamttt.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BABY_LOG_ITEM")
@Builder
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BabyLogItem extends BaseEntity{
    /**
     * 반려동물 별 로그 아이템 아이디
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long babyLogItemId;

    /**
     * 로그 투두 이름
     */
    private String todoName;

    /**
     * baby_log 테이블에 babyLogId 컬럼을 참조
     */
    @ManyToOne
    @JoinColumn(name = "babyLogId", referencedColumnName = "babyLogId")
    private BabyLog babyLog;
}
