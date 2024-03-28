package com.project.teamttt.domain.service;

import com.project.teamttt.api.baby.dto.BabyRequestDto;
import com.project.teamttt.domain.entity.Baby;
import com.project.teamttt.domain.entity.Member;
import com.project.teamttt.domain.repository.jpa.BabyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BabyDomainService {
    private final BabyRepository babyRepository;

    public Baby save(BabyRequestDto.RequestCreate requestCreate, Member member) {
        return babyRepository.save(
                Baby.builder()
                        .member(member)
                        .babyName(requestCreate.getBabyName())
                        .birth(requestCreate.getBirth())
                        .sex(requestCreate.getSex())
                        .animalType(requestCreate.getAnimalType())
                        .nickname(requestCreate.getNickname())
                        .reminder(requestCreate.getReminder())
                        .isPublic(requestCreate.getIsPublic())
                        .build()
        );
    }

    public Baby save(BabyRequestDto.RequestUpdate RequestUpdate, Member member) {
        return babyRepository.save(
                Baby.builder()
                        .member(member)
                        .babyId(RequestUpdate.getBabyId())
                        .babyName(RequestUpdate.getBabyName())
                        .birth(RequestUpdate.getBirth())
                        .sex(RequestUpdate.getSex())
                        .animalType(RequestUpdate.getAnimalType())
                        .nickname(RequestUpdate.getNickname())
                        .reminder(RequestUpdate.getReminder())
                        .isPublic(RequestUpdate.getIsPublic())
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
}
