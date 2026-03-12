package kr.co.kepco.pms.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 1. 파라미터 검증 실패나 잘못된 요청일 때 (HTTP 400)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(e.getMessage());
        return createErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    /**
     * 2. 데이터베이스에서 값을 찾지 못했을 때 (예: NullPointerException 등, HTTP 404)
     * (실무에서는 Custom Exception을 만들어 쓰는 것을 권장합니다)
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, Object>> handleNullPointerException(NullPointerException e) {
        log.error(e.getMessage());
        return createErrorResponse(HttpStatus.NOT_FOUND, "요청하신 데이터를 찾을 수 없습니다.");
    }

    /**
     * 3. 위에서 잡지 못한 모든 예상치 못한 에러 (최상위 Exception, HTTP 500)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllException(Exception e) {
        log.error(e.getMessage());
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다. 관리자에게 문의하세요.");
    }

    /**
     * 에러 응답 포맷을 통일하기 위한 공통 메서드
     */
    private ResponseEntity<Map<String, Object>> createErrorResponse(HttpStatus status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value()); // 예: 400, 404, 500
        response.put("error", status.getReasonPhrase()); // 예: Bad Request
        response.put("message", message);

        return ResponseEntity.status(status).body(response);
    }
}
