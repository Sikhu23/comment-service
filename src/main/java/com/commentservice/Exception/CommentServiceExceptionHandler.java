package com.commentservice.Exception;


import com.commentservice.Model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class CommentServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CommentNotFoundException.class})
    ResponseEntity customerNotFoundHandler(Exception exception, ServletWebRequest request){
        ApiError apiError = new ApiError();
        apiError.setMessage(exception.getMessage());
        apiError.setCode("404");
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

//
//    @ExceptionHandler({CustomerAlreadyExistsException.class})
//    ResponseEntity customerAlreadyExistsExceptionHandler(Exception exception, ServletWebRequest request){
//        ApiError apiError = new ApiError();
//        apiError.setStatus(HttpStatus.NOT_FOUND);
//        apiError.setErrors(Arrays.asList(exception.getMessage()));
//        apiError.setPath(request.getDescription(false));
//        apiError.setTimestamp(LocalDateTime.now());
//        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
//    }

//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  HttpHeaders headers, HttpStatus status,
//                                                                  WebRequest request) {
//
//        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
//
//        List<String> errors = fieldErrors.stream()
//                .map(err -> err.getField() + " : " + err.getDefaultMessage())
//                .collect(Collectors.toList());
//
//
//        ApiError apiError = new ApiError();
//        apiError.setCode();
//        apiError.setMessage();
//
//        return new ResponseEntity<>(apiError, headers, apiError.getCode());
//    }
}
