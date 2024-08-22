package heroes.domain.bookmark.api;

import heroes.domain.bookmark.application.CompanyBookmarkService;
import heroes.domain.company.dto.response.CompanyUnitResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "북마크 API", description = "북마크 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmarks/companies")
public class CompanyBookmarkController {
    private final CompanyBookmarkService bookmarkService;

    @Operation(summary = "기업 북마크 적용", description = "지정한 기업에 북마크를 적용합니다.")
    @PostMapping("/{companyId}")
    public ResponseEntity<Void> createBookmark(@PathVariable Long companyId) {
        bookmarkService.createCompanyBookmark(companyId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "기업 북마크 해제", description = "지정한 기업에 적용했던 북마크를 해제합니다.")
    @DeleteMapping("/{companyId}")
    public ResponseEntity<Void> deleteBookmark(@PathVariable Long companyId) {
        bookmarkService.deleteCompanyBookmark(companyId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "북마크 적용 기업 리스트 조회", description = "접근 유저가 북마크한 기업 목록 리스트를 조회합니다.")
    @GetMapping
    public Slice<CompanyUnitResponse> getBookmarkedCompanyList(
            @Parameter(description = "이전 페이지의 마지막 기업 ID (첫 페이지는 비워두세요.)")
                    @RequestParam(required = false)
                    Long lastCompanyId,
            @Parameter(description = "페이지당 기업 수", example = "1") @RequestParam(value = "size")
                    int pageSize) {
        return bookmarkService.getBookmarkedCompanyList(lastCompanyId, pageSize);
    }
}
