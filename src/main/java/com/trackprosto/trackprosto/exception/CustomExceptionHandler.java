package com.trackprosto.trackprosto.exception;

import com.trackprosto.trackprosto.model.response.TemplateResponse;
import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.ControllerAdvice;
        import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<TemplateResponse<Void>> handleCustomException(CustomException ex) {
        TemplateResponse<Void> res = new TemplateResponse<>(ex.getMessage(), null);
        return new ResponseEntity<>(res, ex.getHttpStatus());
    }
    public static class CustomException extends RuntimeException {
        private HttpStatus httpStatus;

        public CustomException(String message, HttpStatus httpStatus) {
            super(message);
            this.httpStatus = httpStatus;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }
    }
}

//throw new CustomException("Terjadi kesalahan", HttpStatus.INTERNAL_SERVER_ERROR);
