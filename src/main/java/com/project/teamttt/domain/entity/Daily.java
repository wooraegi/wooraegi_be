package com.project.teamttt.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "Diary")
@Builder
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Diary extends BaseEntity{
    /**
     * 데일리 아이디
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dailyId;

    /**
     * 데일리 제목
     */
    private String title;

    /**
     * 데일리 내용
     */
    private String content;

    /**
     * 데일리 공개 여부
     */
    private Boolean isPublic;

    /**
     * member 테이블에 memberId 컬럼을 참조
     */
    @ManyToOne
    @JoinColumn(name = "memberId", referencedColumnName = "memberId")
    private Member member;
}
