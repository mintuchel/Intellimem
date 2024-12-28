package enpalmer.intellimem.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TodoErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 TODO 입니다");

    private final HttpStatus status;
    private final String message;
}
