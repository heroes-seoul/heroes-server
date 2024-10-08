package heroes.domain.type.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {
    RESTAURANT("식당"),
    CAFE("카페"),
    KIDSCAFE("키즈카페"),
    PLAYGROUND("놀이시설"),
    ETC("기타");
    private final String value;
}
