package heroes.domain.companyhour.dto;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Getter
public class CompanyHourCreateRequest {
    private String dayOfWeek;

    @DateTimeFormat(pattern ="HH:mm")
    private LocalTime startTime;
    @DateTimeFormat(pattern ="HH:mm")
    private LocalTime endTime;
}
