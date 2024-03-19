package com.project.teamttt.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "DAILY")
@Builder
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Daily extends BaseEntity{
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
     * member 테이블에 member_id 컬럼을 참조
     */
    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private Member member;
}
