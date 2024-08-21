package heroes.domain.review.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class CompanyReviewDto {
    private final String profileUrl;
    private final String nickname;
    private final String reviewValue;

    @QueryProjection
    public CompanyReviewDto(String profileUrl, String nickname, String reviewValue) {
        this.profileUrl = profileUrl;
        this.nickname = nickname;
        this.reviewValue = reviewValue;
    }
}
