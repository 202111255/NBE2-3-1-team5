package com.example.coffee.common.exception;

import com.example.coffee.common.Result;
import com.example.coffee.common.ResultCode;
import io.jsonwebtoken.JwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Validation 실패 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        Result<Map<String, String>> result = new Result<>(ResultCode.INVALID_REQUEST, errors);
        return ResponseEntity.status(ResultCode.INVALID_REQUEST.getCode()).body(result);
    }

    // JSON 파싱 오류
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Result<Void>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Result<Void> result = new Result<>(ResultCode.INVALID_JSON, null);
        return ResponseEntity.status(ResultCode.INVALID_REQUEST.getCode()).body(result);
    }

    // HTTP 메서드 오류
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Result<Void>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        Result<Void> result = new Result<>(ResultCode.UNSUPPORTED_HTTP_METHOD, null);
        return ResponseEntity.status(ResultCode.UNSUPPORTED_HTTP_METHOD.getCode()).body(result);
    }

    // 요청 파라미터 누락
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Result<Map<String, String>>> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getParameterName(), "필수 파라미터가 누락되었습니다.");
        Result<Map<String, String>> result = new Result<>(ResultCode.MISSING_PARAMETER, errors);
        return ResponseEntity.status(ResultCode.MISSING_PARAMETER.getCode()).body(result);
    }




    // 기타 예외 처리
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Result<Void>> handleGeneralException(Exception ex) {
//        ex.printStackTrace();
//        Result<Void> result = new Result<>(ResultCode.ETC_ERROR, null);
//        return ResponseEntity.status(ResultCode.ETC_ERROR.getCode()).body(result);
//    }
}
