package heroes.domain.member.application;

import static heroes.global.common.constants.PresignedUrlConstants.MEMBER_PROFILE_DIRECTORY;

import heroes.domain.common.presignedurl.application.PresignedUrlService;
import heroes.domain.common.presignedurl.dto.response.PresignedUrlIssueResponse;
import heroes.domain.member.domain.Member;
import heroes.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final PresignedUrlService presignedUrlService;
    private final MemberUtil memberUtil;

    public PresignedUrlIssueResponse getImageUploadUrl() {
        final Member member = memberUtil.getCurrentMember();
        return presignedUrlService.generatePresignedUrl(
                MEMBER_PROFILE_DIRECTORY + member.getId().toString());
    }
}
