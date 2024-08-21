package heroes.domain.companyhour.dto;

import com.querydsl.core.annotations.QueryProjection;
import heroes.domain.companyhour.domain.DayOfWeek;
import java.time.LocalTime;
import lombok.Getter;

@Getter
public class CompanyHourDto {
    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;
    private final LocalTime endTime;

    @QueryProjection
    public CompanyHourDto(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
