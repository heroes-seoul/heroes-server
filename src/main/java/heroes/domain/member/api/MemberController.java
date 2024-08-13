package heroes.domain.member.api;

import heroes.domain.common.presignedurl.dto.response.PresignedUrlIssueResponse;
import heroes.domain.member.application.MemberService;
import heroes.domain.member.dto.request.MemberUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "멤버 API", description = "일반 사용자 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "프로필 url 발급", description = "프로필 업로드를 위한 presigned url을 발급합니다.")
    @GetMapping("/generate-profile-url")
    public PresignedUrlIssueResponse getImageUploadUrl() {
        return memberService.getImageUploadUrl();
    }

    @Operation(summary = "회원 정보 수정", description = "회원 정보를 수정합니다.")
    @PutMapping("/info")
    public ResponseEntity<Void> updateMemberInfo(@RequestBody MemberUpdateRequest request) {
        memberService.updateMember(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
