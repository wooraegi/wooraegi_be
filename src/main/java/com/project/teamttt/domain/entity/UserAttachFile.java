package com.project.teamttt.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "USER_ATTACH_FILE")
@Builder
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class UserAttachFile extends BaseEntity{
    /**
     * 첨부파일 아이디
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attachFileId;

    /**
     * 참조하는 id 종류 - baby_id, dialy_id, member_id
     */
    private String refId;

    /**
     * 사용되는 카테고리 종류 - MY_PROFILE, BABY_PROFILE, DIARY
     */
    private String refType;

    /**
     * 첨부파일 url
     */
    private String fileUrl;

    /**
     * 첨부파일 현재 사용 여부
     */
    private Boolean isUsed;

    /**
     * members 테이블에 memberId 컬럼을 참조
     */
    @ManyToOne
    @JoinColumn(name = "memberId", referencedColumnName = "memberId")
    private Member member;
}
