package com.project.teamttt.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Members")
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor

public class Member extends BaseEntity{

    /**
     * 맴버 아이디 (PK)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    /**
     * 맴버 이메일
     */
    private String email;

    /**
     * 맴버 패스워드
     */
    private String password;

    /**
     * 맴버 닉네임
     */
    private String nickname;

    /**
     * 소셜 이름
     */
    private String social;
}
