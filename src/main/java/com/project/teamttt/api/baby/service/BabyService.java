package com.project.teamttt.api.baby.service;

import com.project.teamttt.api.baby.dto.BabyRequestDto;
import com.project.teamttt.api.baby.dto.BabyRequestDto.ResponseBaby;
import com.project.teamttt.api.util.ResponseDto;
import com.project.teamttt.domain.entity.Baby;
import com.project.teamttt.domain.entity.Member;
import com.project.teamttt.domain.service.BabyDomainService;
import com.project.teamttt.domain.service.MemberDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BabyService {

    private final BabyDomainService babyDomainService;
    private final MemberDomainService memberDomainService;

    @Transactional
    public ResponseDto<String> createBaby(BabyRequestDto.RequestCreate requestCreate) {
        Long memberId = requestCreate.getMemberId();
        Member member = memberDomainService.findByMemberId(memberId);
        try {
            babyDomainService.save(requestCreate, member);
            return new ResponseDto<>(true, "SUCCESS CREATE BABY", null);
        } catch (Exception e) {
            return new ResponseDto<>(false, "FAILED TO CREATE BABY: " + e.getMessage(), null);
        }
    }

    @Transactional
    public ResponseDto<String> updateBaby(BabyRequestDto.RequestUpdate requestUpdate) {
        try {
        Long memberIdByLogin = requestUpdate.getMemberId();
        Member member = memberDomainService.findByMemberId(memberIdByLogin);

        Baby baby = babyDomainService.findByBabyId(requestUpdate.getBabyId());
        Long memberIdByBaby = baby.getMember().getMemberId();

            if(memberIdByLogin.equals(memberIdByBaby)){

                babyDomainService.save(requestUpdate, member);
                return new ResponseDto<>(true, "SUCCESS UPDATE BABY", null);

            }else {
                return new ResponseDto<>(false, "MEMBER ID IS DIFFERENT", null);
            }
        } catch (Exception e) {
            return new ResponseDto<>(false, "FAILED TO UPDATE BABY: " + e.getMessage(), null);
        }
    }

    @Transactional
    public ResponseDto<String> deleteBaby(Long babyId, Long memberId) {
        try {

            Baby baby = babyDomainService.findByBabyId(babyId);
            Long memberIdByBaby = baby.getMember().getMemberId();

            if(memberId.equals(memberIdByBaby)){
                babyDomainService.deleteByBabyId(babyId);
                return new ResponseDto<>(true, "SUCCESS DELETE BABY", null);

            }else {
                return new ResponseDto<>(false, "MEMBER ID IS DIFFERENT", null);
            }
        } catch (Exception e) {
            return new ResponseDto<>(false, "FAILED TO DELETE BABY: " + e.getMessage(), null);
        }
    }

    @Transactional(readOnly = true)
    public ResponseDto<List<ResponseBaby>> getBabyList(Long memberId) {
        try {
                Member member = memberDomainService.findByMemberId(memberId);
                List<ResponseBaby> babyList = babyDomainService.getBabyListByMemberId(member)
                        .stream()
                        .map(ResponseBaby::of)
                        .collect(Collectors.toList());

                return new ResponseDto<>(true, "SUCCESS GET BABYLIST", babyList);

        } catch (Exception e) {
            return new ResponseDto<>(false, "FAILED TO GET BABYLIST: " + e.getMessage(), null);
        }
    }

}
