package com.project.teamttt.api.baby.service;

import com.project.teamttt.api.attachFile.service.S3ImageService;
import com.project.teamttt.api.baby.dto.BabyRequestDto;
import com.project.teamttt.api.baby.dto.BabyRequestDto.ResponseBaby;
import com.project.teamttt.api.util.ResponseDto;
import com.project.teamttt.domain.entity.Baby;
import com.project.teamttt.domain.entity.Member;
import com.project.teamttt.domain.entity.UserAttachFile;
import com.project.teamttt.domain.repository.jpa.UserAttachFileRepository;
import com.project.teamttt.domain.service.BabyDomainService;
import com.project.teamttt.domain.service.MemberDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BabyService {

    private final BabyDomainService babyDomainService;
    private final MemberDomainService memberDomainService;
    private final S3ImageService s3ImageService;
    private final UserAttachFileRepository userAttachFileRepository;

    @Transactional
    public ResponseDto<String> createBaby(@RequestPart(name="requestCreate") BabyRequestDto.RequestCreate requestCreate, @RequestPart(name="image") List<MultipartFile> image) {
        try {
            Long memberId = requestCreate.getMemberId();
            Member member = memberDomainService.findByMemberId(memberId);

            Baby savedBaby = babyDomainService.save(requestCreate, member);

            String s3Url ="";
            if(image != null){
               s3Url = s3ImageService.upload(image.get(0));
            }else{
                s3Url = ""; //반려동물 기본 프로필이미지 s3 업로드 url 넣기
            }

            UserAttachFile userAttachFile = new UserAttachFile();
            userAttachFile.setRefId(savedBaby.getBabyId().toString());
            userAttachFile.setRefType("BABY_PROFILE");
            userAttachFile.setFileUrl(s3Url);
            userAttachFile.setMember(member);
            userAttachFile.setIsUsed(true);

            userAttachFileRepository.save(userAttachFile);

            return new ResponseDto<>(true, "SUCCESS CREATE BABY", null);
        } catch (Exception e) {
            return new ResponseDto<>(false, "FAILED TO CREATE BABY: " + e.getMessage(), null);
        }
    }

    @Transactional
    public ResponseDto<String> updateBaby(@RequestPart(name="requestUpdate") BabyRequestDto.RequestUpdate requestUpdate, @RequestPart(name="image") List<MultipartFile> image) {
        try {
        Long memberIdByLogin = requestUpdate.getMemberId();
        Member member = memberDomainService.findByMemberId(memberIdByLogin);

        Baby baby = babyDomainService.findByBabyId(requestUpdate.getBabyId());
        Long memberIdByBaby = baby.getMember().getMemberId();

            if(memberIdByLogin.equals(memberIdByBaby)){
                Baby savedBaby = babyDomainService.save(requestUpdate, member);

                List<UserAttachFile> fileList = babyDomainService.getFileListByRefId(savedBaby.getBabyId().toString());

                for (UserAttachFile file : fileList) {
                    UserAttachFile unusedFile = new UserAttachFile();
                    unusedFile.setAttachFileId(file.getAttachFileId());
                    unusedFile.setRefId(file.getRefId());
                    unusedFile.setRefType(file.getRefType());
                    unusedFile.setFileUrl(file.getFileUrl());
                    unusedFile.setMember(file.getMember());
                    unusedFile.setIsUsed(false);
                    userAttachFileRepository.save(unusedFile);
                }

                String s3Url = s3ImageService.upload(image.get(0));

                UserAttachFile userAttachFile = new UserAttachFile();
                userAttachFile.setRefId(savedBaby.getBabyId().toString());
                userAttachFile.setRefType("BABY_PROFILE");
                userAttachFile.setFileUrl(s3Url);
                userAttachFile.setMember(member);
                userAttachFile.setIsUsed(true);

                userAttachFileRepository.save(userAttachFile);

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
                List<UserAttachFile> fileList = babyDomainService.getFileListByRefId(babyId.toString());

                for (UserAttachFile file : fileList) {
                    s3ImageService.deleteImageFromS3(file.getFileUrl());
                    userAttachFileRepository.deleteByAttachFileId(file.getAttachFileId());
                }

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
                        .map(baby -> ResponseBaby.of(baby, ""))
                        .collect(Collectors.toList());

                for (ResponseBaby baby : babyList) {
                UserAttachFile file = babyDomainService.findByRefIdAndIsUsedTrue(baby.getBabyId().toString());
                baby.setFileUrl(file.getFileUrl());
                }

                return new ResponseDto<>(true, "SUCCESS GET BABYLIST", babyList);

        } catch (Exception e) {
            return new ResponseDto<>(false, "FAILED TO GET BABYLIST: " + e.getMessage(), null);
        }
    }

}
