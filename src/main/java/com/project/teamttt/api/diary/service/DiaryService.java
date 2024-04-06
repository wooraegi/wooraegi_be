package com.project.teamttt.api.diary.service;

import com.project.teamttt.api.attachFile.service.S3ImageService;
import com.project.teamttt.api.baby.dto.BabyRequestDto;
import com.project.teamttt.api.diary.dto.DiaryRequestDto;
import com.project.teamttt.api.util.ResponseDto;
import com.project.teamttt.domain.entity.Diary;
import com.project.teamttt.domain.entity.Member;
import com.project.teamttt.domain.entity.UserAttachFile;
import com.project.teamttt.domain.repository.jpa.UserAttachFileRepository;
import com.project.teamttt.domain.service.DiaryDomainService;
import com.project.teamttt.domain.service.MemberDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryDomainService diaryDomainService;
    private final MemberDomainService memberDomainService;
    private final S3ImageService s3ImageService;
    private final UserAttachFileRepository userAttachFileRepository;


    @Transactional
    public ResponseDto<String> createDiary(DiaryRequestDto.RequestCreate requestCreate, List<MultipartFile> imageFileList) {
        Long memberId = requestCreate.getMemberId();
        Member member = memberDomainService.findByMemberId(memberId);
        try {
            Diary diary = diaryDomainService.save(requestCreate, member);

            for(MultipartFile imageFile : imageFileList){
                String s3Url ="";
                if(imageFile != null){
                    s3Url = s3ImageService.upload(imageFile);
                }else{
                    s3Url = "https://wooraegi-bucket.s3.ap-northeast-2.amazonaws.com/c0bf5e53-dwooraegi_baby_profile.JPG";
                }
                UserAttachFile userAttachFile = new UserAttachFile();
                userAttachFile.setRefId(diary.getDiaryId());
                userAttachFile.setRefType("MEMBER_DIARY");
                userAttachFile.setFileUrl(s3Url);
                userAttachFile.setMember(member);
                userAttachFile.setIsUsed(true);

                userAttachFileRepository.save(userAttachFile);
            }
            return new ResponseDto<>(true, "SUCCESS CREATE DIARY", null);
        } catch (Exception e) {
            return new ResponseDto<>(false, "FAILED TO CREATE DIARY: " + e.getMessage(), null);
        }
    }

    @Transactional
    public ResponseDto<String> updateDiary(DiaryRequestDto.RequestUpdate requestUpdate, List<MultipartFile> imageFileList) {
        try {
            Long memberIdByLogin = requestUpdate.getMemberId();
            Member member = memberDomainService.findByMemberId(memberIdByLogin);

            Diary diary = diaryDomainService.findByDiaryId(requestUpdate.getDiaryId());
            Long memberIdByDiary = diary.getMember().getMemberId();


            if(memberIdByLogin.equals(memberIdByDiary)){

                diaryDomainService.save(requestUpdate, member);

                List<UserAttachFile> fileList = diaryDomainService.getFileListByRefId(diary.getDiaryId());

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

                for(MultipartFile imageFile : imageFileList){

                    if (imageFile != null) {

                        String s3Url = s3ImageService.upload(imageFile);

                        UserAttachFile userAttachFile = new UserAttachFile();
                        userAttachFile.setRefId(diary.getDiaryId());
                        userAttachFile.setRefType("MEMBER_DIARY");
                        userAttachFile.setFileUrl(s3Url);
                        userAttachFile.setMember(member);
                        userAttachFile.setIsUsed(true);

                        userAttachFileRepository.save(userAttachFile);
                    }
                }
                return new ResponseDto<>(true, "SUCCESS UPDATE DIARY", null);

            }else {
                return new ResponseDto<>(false, "MEMBER ID IS DIFFERENT", null);
            }
        } catch (Exception e) {
            return new ResponseDto<>(false, "FAILED TO UPDATE DIARY: " + e.getMessage(), null);
        }
    }

    @Transactional
    public ResponseDto<String> deleteDiary(Long diaryId, Long memberId) {
        try {

            Diary diary = diaryDomainService.findByDiaryId(diaryId);
            Long memberIdByDiary = diary.getMember().getMemberId();
            List<UserAttachFile> fileList = diaryDomainService.getFileListByRefId(diary.getDiaryId());

            if(memberId.equals(memberIdByDiary)){

                for (UserAttachFile file : fileList) {
                    s3ImageService.deleteImageFromS3(file.getFileUrl());
                    userAttachFileRepository.deleteByAttachFileId(file.getAttachFileId());
                }

                diaryDomainService.deleteByDiaryId(diaryId);
                return new ResponseDto<>(true, "SUCCESS DELETE DIARY", null);

            }else {
                return new ResponseDto<>(false, "MEMBER ID IS DIFFERENT", null);
            }
        } catch (Exception e) {
            return new ResponseDto<>(false, "FAILED TO DELETE DIARY: " + e.getMessage(), null);
        }
    }

    @Transactional(readOnly = true)
    public ResponseDto<List<DiaryRequestDto.ResponseDiary>> getDiaryList(Long memberId) {
        try {
            Member member = memberDomainService.findByMemberId(memberId);
            List<DiaryRequestDto.ResponseDiary> diaryList = diaryDomainService.getDiaryListByMemberId(member)
                    .stream()
                    .map(DiaryRequestDto.ResponseDiary::of)
                    .collect(Collectors.toList());

            return new ResponseDto<>(true, "SUCCESS GET DIARYLIST", diaryList);

        } catch (Exception e) {
            return new ResponseDto<>(false, "FAILED TO GET DIARYLIST: " + e.getMessage(), null);
        }
    }

    @Transactional(readOnly = true)
    public ResponseDto<DiaryRequestDto.ResponseDiaryDetail> getDiaryById(Long diaryId) {
        try {
            Diary diary = diaryDomainService.findByDiaryId(diaryId);
            List<UserAttachFile> fileList = diaryDomainService.getFileListByRefIdAndIsUsedTrue(diary.getDiaryId());
            List<String> fileUrlList = new ArrayList<>();
            for(UserAttachFile usedFile : fileList){
                fileUrlList.add(usedFile.getFileUrl());
            }
            DiaryRequestDto.ResponseDiaryDetail responseDiary = DiaryRequestDto.ResponseDiaryDetail.of(diary, fileUrlList);

            return new ResponseDto<>(true, "SUCCESS GET DIARY", responseDiary);
        } catch (Exception e) {
            return new ResponseDto<>(false, "FAILED TO GET DIARY: " + e.getMessage(), null);
        }
    }
}