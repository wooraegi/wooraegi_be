package com.project.teamttt.domain.service;

import com.project.teamttt.api.baby.dto.BabyRequestDto;
import com.project.teamttt.domain.entity.Baby;
import com.project.teamttt.domain.entity.Member;
import com.project.teamttt.domain.entity.UserAttachFile;
import com.project.teamttt.domain.repository.jpa.BabyRepository;
import com.project.teamttt.domain.repository.jpa.UserAttachFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BabyDomainService {
    private final BabyRepository babyRepository;
    private final UserAttachFileRepository userAttachFileRepository;

    public Baby save(BabyRequestDto requestCreateBaby, Member member) {
        return babyRepository.save(
                Baby.builder()
                        .member(member)
                        .babyName(requestCreateBaby.getBabyName())
                        .birth(requestCreateBaby.getBirth())
                        .sex(requestCreateBaby.getSex())
                        .animalType(requestCreateBaby.getAnimalType())
                        .nickname(requestCreateBaby.getNickname())
                        .reminder(requestCreateBaby.getReminder())
                        .isPublic(requestCreateBaby.getIsPublic())
                        .build()
        );
    }

    public Baby save(BabyRequestDto.RequestUpdate requestUpdate, Member member) {
        return babyRepository.save(
                Baby.builder()
                        .member(member)
                        .babyId(requestUpdate.getBabyId())
                        .babyName(requestUpdate.getBabyName())
                        .birth(requestUpdate.getBirth())
                        .sex(requestUpdate.getSex())
                        .animalType(requestUpdate.getAnimalType())
                        .nickname(requestUpdate.getNickname())
                        .reminder(requestUpdate.getReminder())
                        .isPublic(requestUpdate.getIsPublic())
                        .build()
        );
    }

    public Baby findByBabyId(Long babyId) {
        return babyRepository.findByBabyId(babyId);
    }

    public Boolean deleteByBabyId(Long babyId) {
        if(babyRepository.deleteByBabyId(babyId) > 0){
            return true;
        }
        return false;
    }

    public List<Baby> getBabyListByMemberId(Member member) {
        return babyRepository.findByMember(member);
    }

    public List<UserAttachFile> findByRefIdAndIsUsedTrue(String refId) {
        return userAttachFileRepository.findByRefIdAndIsUsedTrue(refId);
    }

    public List<UserAttachFile> getFileListByRefId(String refId) {
        return userAttachFileRepository.findByRefId(refId);
    }

}
