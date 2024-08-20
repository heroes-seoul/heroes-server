package heroes.domain.sublevel.api;

import heroes.domain.common.presignedurl.dto.response.PresignedUrlIssueResponse;
import heroes.domain.sublevel.application.SubLevelService;
import heroes.domain.sublevel.domain.SubLevel;
import heroes.domain.sublevel.dto.request.SubLevelUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "양육단계 API", description = "양육단계 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/verifications")
public class SubLevelController {
    private final SubLevelService subLevelService;

    @Operation(summary = "양육단계 인증 url 발급", description = "양육단계 인증을 위한 presigned url을 발급합니다.")
    @GetMapping("/generate-verify-url/{subLevel}")
    public PresignedUrlIssueResponse getUploadUrl(@RequestParam SubLevel subLevel) {
        return subLevelService.getSubLevelImageUrl(subLevel);
    }

    @Operation(summary = "양육단계 확인 및 증가", description = "양육단계 이미지 업로드 확인 후 이미지 등록 및 양육단계 상승을 진행합니다.")
    @PostMapping
    public ResponseEntity<Void> updateCompanySubLevel(@RequestBody SubLevelUpdateRequest request) {
        subLevelService.updateCompanySubLevel(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
