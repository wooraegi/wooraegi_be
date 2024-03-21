package com.project.teamttt.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "BABY_LOG_HISTORY")
@Builder
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BabyLogHistory extends BaseEntity{
    /**
     * 반려동물 별 로그 히스토리 아이디
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long babyLogHistoryId;

    /**
     * 로그 투두 이름
     */
    private String todoName;

    /**
     * 로그 체크 여부
     */
    private Boolean isChecked;

    /**
     * 사용자가 지정한 로그 날짜
     */
    private Date logDate;

    /**
     * babys테이블에 baby_id 컬럼을 참조
     */
    @ManyToOne
    @JoinColumn(name = "babyId", referencedColumnName = "babyId")
    private Baby baby;
}
