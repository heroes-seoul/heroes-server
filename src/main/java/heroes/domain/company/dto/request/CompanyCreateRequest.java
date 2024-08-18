package heroes.domain.company.dto.request;

import heroes.domain.atmosphere.domain.Atmosphere;
import heroes.domain.companyhour.dto.CompanyHourCreateRequest;
import heroes.domain.type.domain.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;

@Schema(description = "기업유저 정보 기입")
@Getter
public class CompanyCreateRequest {

    @NotNull(message = "기업명은 비워둘 수 없습니다.")
    @Schema(description = "기업명", defaultValue = "히어로즈")
    private String companyName;

    @NotNull(message = "주소는 비워둘 수 없습니다.")
    @Schema(description = "주소", defaultValue = "서울특별시 동작구 여의대방로54길 18")
    private String address;

    @NotNull(message = "상세 주소는 비워둘 수 없습니다.")
    @Schema(description = "상세 주소", defaultValue = "1층")
    private String addressDetail;

    @NotNull(message = "전화번호는 비워둘 수 없습니다.")
    @Schema(description = "전화번호", defaultValue = "02-810-5000")
    private String phoneNumber;

    @Schema(description = "기업소개", defaultValue = "아이가 행복하게 뛰놀수 있는 카페가 있습니다.")
    private String companyDescription;

    @Schema(
            description = "기업관련url",
            defaultValue = "https://www.seoulwomen.or.kr/sfwf/main/index.do")
    private String companyUrl;

    @Schema(description = "운영시간")
    private List<CompanyHourCreateRequest> companyHourCreateRequestList;

    @Schema(description = "기업타입 리스트", defaultValue = "[\"CAFE\", \"RESTAURANT\"]")
    private List<Type> typeList;

    @Schema(description = "분위기 리스트", defaultValue = "[\"ENERGETIC\", \"FUN\", \"SOFT\", \"CALM\"]")
    private List<Atmosphere> atmosphereNameList;

    @Schema(
            description = "기업 메인 이미지",
            defaultValue =
                    "https://heroes-presigned.s3.ap-northeast-2.amazonaws.com/companies/main/1?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20240813T040424Z&X-Amz-SignedHeaders=host&X-Amz-Expires=300&X-Amz-Credential=AKIAVRUVVZYYFMRNKFKI%2F20240813%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Signature=04c8c6b520576ea1b0d5c659a5927bae1667d7e71cefbbf34d35bdac61482073")
    private String companyMainImageUrl;

    //    @Schema(description = "기업 서브 이미지", defaultValue =
    // "[\"https://heroes-presigned.s3.ap-northeast-2.amazonaws.com/companies/sub/1/1?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20240813T040424Z&X-Amz-SignedHeaders=host&X-Amz-Expires=300&X-Amz-Credential=AKIAVRUVVZYYFMRNKFKI%2F20240813%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Signature=04c8c6b520576ea1b0d5c659a5927bae1667d7e71cefbbf34d35bdac61482073\",https://heroes-presigned.s3.ap-northeast-2.amazonaws.com/companies/sub/2/1?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20240813T040424Z&X-Amz-SignedHeaders=host&X-Amz-Expires=300&X-Amz-Credential=AKIAVRUVVZYYFMRNKFKI%2F20240813%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Signature=04c8c6b520576ea1b0d5c659a5927bae1667d7e71cefbbf34d35bdac61482073\" ]")
    @Schema(description = "기업 서브 이미지")
    private List<String> companySubImageUrlList;

    @Schema(
            description = "기업 메뉴이미지",
            defaultValue =
                    "https://heroes-presigned.s3.ap-northeast-2.amazonaws.com/companies/menu/1?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20240813T040424Z&X-Amz-SignedHeaders=host&X-Amz-Expires=300&X-Amz-Credential=AKIAVRUVVZYYFMRNKFKI%2F20240813%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Signature=04c8c6b520576ea1b0d5c659a5927bae1667d7e71cefbbf34d35bdac61482073")
    private String companyMenuImageUrl;
}
