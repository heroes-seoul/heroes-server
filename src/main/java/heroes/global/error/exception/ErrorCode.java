package heroes.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    SAMPLE_ERROR(HttpStatus.BAD_REQUEST, "HS4000", "Sample Error Message"),
    NOT_ALLOWED_SUBLEVEL(HttpStatus.BAD_REQUEST, "HS4001", "아직 업데이트 할 수 없는 양육 단계입니다."),

    EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "HS4010", "만료된 JWT 토큰입니다."),
    ID_TOKEN_VERIFICATION_FAILED(HttpStatus.UNAUTHORIZED, "HS4011", "ID 토큰 검증에 실패했습니다."),
    NOT_EXISTED_ROLE(HttpStatus.UNAUTHORIZED, "HS4012", "존재하지 않는 역할입니다."),
    AUTH_NOT_FOUND(HttpStatus.UNAUTHORIZED, "HS4013", "시큐리티 인증 정보를 찾을 수 없습니다."),
    MISSING_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "HS4014", "토큰 정보가 존재하지 않습니다."),

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "HS4040", "해당 회원을 찾을 수 없습니다."),
    COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "HS4041", "해당 기업을 찾을 수 없습니다."),
    COMPANY_BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "HS4042", "해당 기업에 대한 북마크가 적용되지 않았습니다."),
    BOOKMARKED_COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "HS4043", "북마크된 기업을 찾을 수 없습니다."),

    NICKNAME_ALREADY_EXIST(HttpStatus.CONFLICT, "HS4090", "이미 존재하는 닉네임입니다."),
    COMPANY_BOOKMARK_ALREADY_EXIST(HttpStatus.CONFLICT, "HS4091", "이미 북마크가 적용되었습니다."),

    INVALID_IMAGE_TYPE(HttpStatus.UNPROCESSABLE_ENTITY, "HS4221", "이미지 유형이 잘못되었습니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
