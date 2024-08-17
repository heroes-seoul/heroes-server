package heroes.domain.companyhour.dto;

import heroes.global.common.validations.EnumValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static heroes.global.common.constants.MessageConstants.INVALID_DAYOFWEEK_MESSAGE;

@Getter
public class CompanyHourCreateRequest {
    @NotNull
    @Schema(description = "요일", defaultValue = "MON")
    @EnumValue(enumClass = DayOfWeek.class, message = INVALID_DAYOFWEEK_MESSAGE, ignoreCase = true)
    private String dayOfWeek;

    @NotNull
    @Schema(description = "시작 시간", defaultValue = "10:00")
    @DateTimeFormat(pattern ="HH:mm")
    private LocalTime startTime;

    @NotNull
    @Schema(description = "종료 시간", defaultValue = "11:00")
    @DateTimeFormat(pattern ="HH:mm")
    private LocalTime endTime;
}
