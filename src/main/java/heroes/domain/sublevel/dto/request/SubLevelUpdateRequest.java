package heroes.domain.sublevel.dto.request;

import heroes.domain.sublevel.domain.SubLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubLevelUpdateRequest {

    @Schema(description = "양육단계 코드", defaultValue = "LV0101")
    private SubLevel subLevel;
}
