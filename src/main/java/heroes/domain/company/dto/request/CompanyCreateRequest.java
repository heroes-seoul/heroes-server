package heroes.domain.company.dto.request;

import heroes.domain.companyhour.dto.CompanyHourCreateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;
@Schema(description = "기업유저 정보 기입")
@Getter
public class CompanyCreateRequest {

    private String companyName;

    private String address;

    private String addressDetail;

    private String phoneNumber;

    private String companyDescription;

    private String companyUrl;

    private List<CompanyHourCreateRequest> companyHourCreateRequestList;

    private List<String> companyTypeList;

    private List<String> atmosphereNameList;

    private String companyMainImageUrl;

    private List<String> companySubImageUrlList;

    private String companyMenuImageUrl;
}
