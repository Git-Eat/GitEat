package com.giteat.statistics.service;

import com.giteat.api.LabApi;
import com.giteat.statistics.dto.*;
import com.giteat.statistics.mapper.StatisticsRepoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("StatisticsRepoServiceImpl")
@RequiredArgsConstructor
public class StatisticsRepoServiceImpl implements StatisticsRepoService{

    private final StatisticsRepoMapper statisticsRepoMapper;
    private final LabApi labApi;

    @Override
    public int getTotalCommit(String repoId) {
        return statisticsRepoMapper.getTotalCommit(repoId);
    }

    @Override
    public List<ParticipantsDto> getParticipants(String repoId) {
        return statisticsRepoMapper.getParticipants(repoId);
    }

    @Override
    public MergeRequestTotalDto getMergeReqeustTotal(String repoId) {
        MergeRequestTotalDto mergeRequestTotalDto = new MergeRequestTotalDto();
        List<MergeRequestByUserDto> mergeRequestByUsers = statisticsRepoMapper.getMergeRequestByUser(repoId);
        mergeRequestTotalDto.setTotalMergeRequest(statisticsRepoMapper.getTotalMergeRequest(repoId));

        // prUser와 participants를 비교
        List<ParticipantsDto> getParticipants = statisticsRepoMapper.getParticipants(repoId);
        for(ParticipantsDto participantsDto : getParticipants){
            boolean isFound = false;
            int userId = participantsDto.getUserId();

            // userList에서 해당 userId를 찾기
            for (MergeRequestByUserDto userDto : mergeRequestByUsers) {
                if (userDto.getUserId() == userId) {
                    isFound = true;
                    break;
                }
            }

            // userList에 없으면, 참가자 목록에서 해당 사람을 PR 개수 0으로 추가
            if (!isFound) {
                MergeRequestByUserDto newUserDto = new MergeRequestByUserDto();
                newUserDto.setUserId(userId);
                newUserDto.setUserName(participantsDto.getUserName());
                newUserDto.setAvatarUrl(participantsDto.getAvatarUrl());
                newUserDto.setMergeRequestCount(0);  // PR 개수 0 설정
                mergeRequestByUsers.add(newUserDto);  // userList에 추가
            }
        }
        mergeRequestTotalDto.setUserList(mergeRequestByUsers); //결과 DTO에 userList 설정
        return mergeRequestTotalDto;
    }

    @Override
    public CommentTotalDto getCommentTotal(String repoId) {
        int totalComment = statisticsRepoMapper.getTotalComment(repoId);
        List<CommentByUserDto> commentByUser = statisticsRepoMapper.getCommentByUser(repoId);
        CommentTotalDto commentTotalDto = new CommentTotalDto();
        commentTotalDto.setTotalComment(totalComment); // 총 댓글 및 대댓글 개수 저장

        // commentUser와 participants를 비교
        List<ParticipantsDto> getParticipants = statisticsRepoMapper.getParticipants(repoId);
        for(ParticipantsDto participants : getParticipants){
            boolean isFound = false;
            int userId = participants.getUserId();

            for(CommentByUserDto commentUser : commentByUser){
                if(commentUser.getUserId() == userId){
                    isFound = true;
                    break;
                }
            }

            if(!isFound){
                CommentByUserDto newUserDto = new CommentByUserDto();
                newUserDto.setUserId(userId);
                newUserDto.setUserName(participants.getUserName());
                newUserDto.setName(participants.getName());
                newUserDto.setAvatarUrl(participants.getAvatarUrl());
                newUserDto.setCommentCount(0);
                commentByUser.add(newUserDto);
            }
        }
        commentTotalDto.setUserList(commentByUser);
        return commentTotalDto;
    }

    @Override
    public List<ContributorsDto> getContributors(String repoId) {
        List<ContributorsDto> contributors = new ArrayList<>();
        List<ParticipantsDto> participantsList = statisticsRepoMapper.getParticipants(repoId); // 참여자 정보 조회

        for(ParticipantsDto participants : participantsList){
            ContributorsDto user = new ContributorsDto();
            int userId = participants.getUserId();
            user.setUserId(userId);
            user.setUserName(participants.getUserName());
            user.setName(participants.getName());
            user.setAvatarUrl(participants.getAvatarUrl());

            Map<String, Object> params = new HashMap<>();
            params.put("repoId", repoId);
            params.put("userId", userId);

            // 주별 통계 조회
            List<WeeklyContributorsInfo> weeklyInfo = statisticsRepoMapper.getWeeklyInfo(params);
            int totalMergeRequest = statisticsRepoMapper.getPrCountByUser(params);
            int totalCommit = statisticsRepoMapper.getCommitCountByUser(params);
            int totalComment = statisticsRepoMapper.getCommentCountByUser(params);
            user.setTotalMergeRequest(totalMergeRequest);
            user.setTotalCommit(totalCommit);
            user.setTotalComment(totalComment);
            user.setWeeklyInfo(weeklyInfo);
            contributors.add(user);
        }
        return contributors;
    }

    @Override
    public Map<String, Object> getLanguages(String repoId, String accessToken) {
        Map<String, Object> languages = labApi.getLanguages(repoId, accessToken);
        return languages;
    }
}
