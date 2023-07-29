package com.trackprosto.trackprosto.exception;

import com.trackprosto.trackprosto.model.response.TemplateResponse;
import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<TemplateResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        String errorMessage = "Invalid request: " + bindingResult.getFieldError().getDefaultMessage();
        TemplateResponse<Void> res = new TemplateResponse<>(errorMessage, null);
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
}

//throw new CustomException("Terjadi kesalahan", HttpStatus.INTERNAL_SERVER_ERROR);
