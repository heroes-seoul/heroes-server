package heroes.domain.atmosphere.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Atmosphere {
    NEAT("깔끔한"),
    ENERGETIC("활기찬"),
    FUN("재미있는"),
    SOFT("부드러운"),
    CALM("차분한");

    private String value;

}
