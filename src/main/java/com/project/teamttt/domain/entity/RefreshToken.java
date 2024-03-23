package com.project.teamttt.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "refresh_token")
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RefreshToken {

    /**
     * 리프레시 토큰 id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshTokenId;

    /**
     * 맴버 id
     */
    @Column(name = "member_id", insertable = false, updatable = false)
    private Long memberId;

    /**
     * 리프레시 토큰
     */
    private String refreshToken;

    /**
     * members테이블에 memberId 컬럼을 참조
     */
    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "memberId")
    private Member member;

    public RefreshToken(Long memberId, String refreshToken) {
        this.memberId = memberId;
        this.refreshToken = refreshToken;
    }

    public RefreshToken update(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
        return this;
    }
}