package com.project.teamttt.api.profile.service;

import com.project.teamttt.api.baby.dto.BabyRequestDto;
import com.project.teamttt.api.baby.service.BabyService;
import com.project.teamttt.api.profile.dto.ProfileRequestDto;
import com.project.teamttt.api.util.ResponseDto;
import com.project.teamttt.domain.entity.Member;
import com.project.teamttt.domain.service.MemberDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final BabyService babyService;
    private final MemberDomainService memberDomainService;

    @Transactional(readOnly = true)
    public ResponseDto<ProfileRequestDto.ResponseDetail> findMemberByMemberId(Long memberId) {
        try {
            List<BabyRequestDto.ResponseBaby> babyDetailList = babyService.getBabyList(memberId).getData();
            Member memberDetail = memberDomainService.findByMemberId(memberId);

            ProfileRequestDto.ResponseDetail profileDetail = ProfileRequestDto.ResponseDetail.of(babyDetailList, memberDetail);

            return new ResponseDto<>(true, "SUCCESS GET PROFILE", profileDetail);

        } catch (Exception e) {
            return new ResponseDto<>(false, "FAILED TO GET PROFILE: " + e.getMessage(), null);
        }
    }

}
