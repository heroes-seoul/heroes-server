package heroes.domain.bookmark.api;

import heroes.domain.bookmark.application.CompanyBookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "기업 API", description = "기업 사용자 관련 API입니다.")
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
}
