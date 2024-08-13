package heroes.domain.member.application;

import static heroes.global.common.constants.PresignedUrlConstants.MEMBER_PROFILE_DIRECTORY;
import static heroes.global.common.constants.PresignedUrlConstants.SLASH;

import heroes.domain.common.presignedurl.application.PresignedUrlService;
import heroes.domain.common.presignedurl.dto.response.PresignedUrlIssueResponse;
import heroes.domain.member.dao.MemberRepository;
import heroes.domain.member.domain.Member;
import heroes.domain.member.dto.request.MemberUpdateRequest;
import heroes.global.error.exception.CustomException;
import heroes.global.error.exception.ErrorCode;
import heroes.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final PresignedUrlService presignedUrlService;
    private final MemberRepository memberRepository;
    private final MemberUtil memberUtil;

    public PresignedUrlIssueResponse getImageUploadUrl() {
        final Member currentMember = memberUtil.getCurrentMember();
        return presignedUrlService.generatePresignedUrl(
                MEMBER_PROFILE_DIRECTORY + currentMember.getId().toString());
    }

    public void updateMember(MemberUpdateRequest request) {
        final Member currentMember = memberUtil.getCurrentMember();
        validateNicknameExist(request);
        String profileUrl =
                presignedUrlService.getCloudfrontUrl()
                        + SLASH
                        + MEMBER_PROFILE_DIRECTORY
                        + currentMember.getId().toString();
        currentMember.updateMemberInfo(request, profileUrl);
    }

    private void validateNicknameExist(MemberUpdateRequest request) {
        if (memberRepository.existsByNickname(request.getNickname())) {
            throw new CustomException(ErrorCode.NICKNAME_ALREADY_EXIST);
        }
    }
}
