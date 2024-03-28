package com.project.teamttt.api.profile.dto;

import com.project.teamttt.api.baby.dto.BabyRequestDto;
import com.project.teamttt.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ProfileRequestDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class ResponseDetail {

        /**
         * 멤버 아이디
         */
        private Long memberId;

        /**
         * 멤버 이름
         */
        private String nickname;

        /**
         * 반려동물 리스트
         */
        private List<BabyRequestDto.ResponseBaby> babyList;

        public static ProfileRequestDto.ResponseDetail of(List<BabyRequestDto.ResponseBaby> babyList, Member member) {
            return ResponseDetail.builder()
                    .babyList(babyList)
                    .memberId(member.getMemberId())
                    .nickname(member.getNickname())
                    .build();
        }
    }
}
