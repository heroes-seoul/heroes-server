package heroes.domain.company.api;

import heroes.domain.common.presignedurl.dto.response.PresignedUrlIssueResponse;
import heroes.domain.company.application.CompanyService;
import heroes.domain.company.dto.request.CompanyCreateRequest;
import heroes.domain.company.dto.response.CompanyCreateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "기업 API", description = "기업 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    @Operation(summary = "기업 이미지 url 발급", description = "기업 이미지 업로드를 위한 presigned url을 발급합니다.")
    @GetMapping("/generate-url")
    public List<PresignedUrlIssueResponse> getImageUploadUrl(@RequestParam("imageType") String imageType, @RequestParam("size")int size) {
        return companyService.getImageUploadUrl(imageType, size);
    }

    @Operation(summary = "기업 신규 정보 기입", description = "기업 이미지 업로드를 위한 presigned url을 발급합니다.")
    @PatchMapping("")
    public ResponseEntity<CompanyCreateResponse> updateCompany(@RequestBody CompanyCreateRequest request) {
        CompanyCreateResponse response = companyService.updateCompany(request);
        return ResponseEntity.ok(response);
    }
}
