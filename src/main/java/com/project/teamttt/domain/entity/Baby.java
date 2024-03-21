package com.project.teamttt.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BABYS")
@Builder
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Baby extends BaseEntity{

    /**
     * 반려동물 아이디
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long babyId;

    /**
     * 반려동물 이름
     */
    private String babyName;

    /**
     * 반려동물 생일
     */
    private String birth;

    /**
     * 반려동물 성별
     */
    private String sex;

    /**
     * 반려동물 종류
     */
    private String animalType;

    /**
     * 반려동물 별명
     */
    private String nickname;

    /**
     * 반려동물 메모사항
     */
    private String reminder;

    /**
     * 반려동물 공개여부
     */
    private Boolean isPublic;

    /**
     * members테이블에 member_id 컬럼을 참조
     */
    @ManyToOne
    @JoinColumn(name = "memberId", referencedColumnName = "memberId")
    private Member member;

}
