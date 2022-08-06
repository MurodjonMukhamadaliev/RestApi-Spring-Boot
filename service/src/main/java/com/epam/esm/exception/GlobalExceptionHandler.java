package com.epam.esm.exception;

import com.epam.esm.DTO.response.ErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ex
                        .getFieldErrors()
                        .stream()
                        .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFoundException(NotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setErrorCode(ErrorCodeStatus.NOT_FOUND.getCode());
        errorDTO.setErrorMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleAlreadyExistsException(AlreadyExistsException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setErrorCode(ErrorCodeStatus.CONFLICT.getCode());
        errorDTO.setErrorMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDTO);
    }

    @ExceptionHandler(NotValidException.class)
    public ResponseEntity<ErrorDTO> handleNotValidException(NotValidException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setErrorCode(ErrorCodeStatus.BAD_REQUEST.getCode());
        errorDTO.setErrorMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleRelationshipException() {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setErrorCode(ErrorCodeStatus.RELATIONSHIP.getCode());
        errorDTO.setErrorMessage("This entity has relationship with other entity");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }
}
