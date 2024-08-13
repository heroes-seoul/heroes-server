package heroes.domain.company.api;

import heroes.domain.common.presignedurl.dto.response.PresignedUrlIssueResponse;
import heroes.domain.company.application.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
