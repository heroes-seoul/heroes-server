package heroes.domain.company.api;

import heroes.domain.atmosphere.domain.Atmosphere;
import heroes.domain.common.presignedurl.dto.response.PresignedUrlIssueResponse;
import heroes.domain.company.application.CompanyService;
import heroes.domain.company.dto.request.CompanyCreateRequest;
import heroes.domain.company.dto.request.CompanyUpdateRequest;
import heroes.domain.company.dto.response.CompanyChangeResponse;
import heroes.domain.company.dto.response.CompanyDetailResponse;
import heroes.domain.company.dto.response.CompanyUnitResponse;
import heroes.domain.type.domain.Type;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Tag(name = "기업 API", description = "기업 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    @Operation(summary = "기업 이미지 url 발급", description = "기업 이미지 업로드를 위한 presigned url을 발급합니다.")
    @GetMapping("/generate-url")
    public List<PresignedUrlIssueResponse> getImageUploadUrl(
            @RequestParam("imageType") String imageType, @RequestParam("size") int size) {
        return companyService.getImageUploadUrl(imageType, size);
    }

    @Operation(summary = "기업 신규 정보 기입", description = "기업 정보 신규로 생성합니다.")
    @PatchMapping()
    public CompanyChangeResponse createCompany(@RequestBody CompanyCreateRequest request) {
        return companyService.createCompany(request);
    }

    @Operation(summary = "기업 정보 수정", description = "기업 정보 신규로 생성합니다.")
    @PatchMapping("/update")
    public CompanyChangeResponse updateCompany(@RequestBody CompanyUpdateRequest request) {
        return companyService.updateCompany(request);
    }

    @Operation(summary = "기업 메인 화면 리스트 전달", description = "메인 화면에 필요한 리스트를 전달합니다. ")
    @GetMapping
    public Slice<CompanyUnitResponse> getCompany(
            @Parameter(description = "페이징 정보") @PageableDefault(size = 10) Pageable pageable) {
        return companyService.getCompanyList(pageable);
    }

    @Operation(summary = "기업 상세 정보 조회", description = "기업 상세 정보를 조회합니다.")
    @GetMapping("/{companyId}")
    public CompanyDetailResponse getCompanyDetailInfo(@PathVariable Long companyId) {
        return companyService.getCompanyDetailInfo(companyId);
    }

    @Operation(summary = "기업 필터링 및 검색 조회", description = "기업에 대하여 필터링 및 검색 조회합니다.")
    @GetMapping("/search")
    public Slice<CompanyUnitResponse> searchCompanies(
            @Parameter(description = "검색할 기업 명") @RequestParam(required = false) String companyName,
            @Parameter(
                            description = "기업 유형",
                            schema =
                                    @Schema(
                                            type = "array",
                                            allowableValues = {
                                                "RESTAURANT",
                                                "CAFE",
                                                "KIDSCAFE",
                                                "PLAYGROUND",
                                                "ETC"
                                            }),
                            style = ParameterStyle.FORM,
                            explode = Explode.TRUE)
                    @RequestParam(required = false)
                    List<Type> types,
            @Parameter(
                            description = "기업 분위기",
                            schema =
                                    @Schema(
                                            type = "array",
                                            allowableValues = {
                                                "NEAT",
                                                "ENERGETIC",
                                                "FUN",
                                                "SOFT",
                                                "CALM"
                                            }),
                            style = ParameterStyle.FORM,
                            explode = Explode.TRUE)
                    @RequestParam(required = false)
                    List<Atmosphere> atmospheres,
            @Parameter(description = "지정할 페이지 내 기업 수") @RequestParam(defaultValue = "10")
                    int pageSize,
            @Parameter(description = "이전 페이지의 마지막 기업 ID (첫 페이지는 비워두세요.)")
                    @RequestParam(required = false)
                    Long lastCompanyId) {
        return companyService.searchCompanies(
                companyName, types, atmospheres, pageSize, lastCompanyId);
    }
}
