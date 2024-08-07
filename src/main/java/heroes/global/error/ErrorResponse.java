package heroes.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private final String errorClassName;
    private final String errorCode;
    private final String message;

    public static ErrorResponse of(String errorClassName, String errorCode, String message) {
        return new ErrorResponse(errorClassName, errorCode, message);
    }
}
