package com.example.catalogmanager.error;

import com.example.catalogmanager.error.exception.GeneralException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final String INVALID_PARAMETER_TEMPLATE = "Invalid parameter: %s";

  private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler
  public ResponseEntity<ErrorDto> handleGenericException(GeneralException exception) {
    logger.error(exception.getMessage(), exception);
    ErrorDto error =
        new ErrorDto(List.of(new FaultDto(exception.getMessage(), exception.getReason())));
    return ResponseEntity.status(exception.getStatus()).body(error);
  }

  @ExceptionHandler
  public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException exception) {
    logger.error(exception.getMessage(), exception);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handleFieldErrors(exception));
  }

  @ExceptionHandler
  public ResponseEntity<ErrorDto> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException exception) {
    logger.error(exception.getMessage(), exception);
    ErrorDto error =
        new ErrorDto(
            List.of(
                new FaultDto(
                    INVALID_PARAMETER_TEMPLATE.formatted(exception.getPropertyName()),
                    exception.getMessage())));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler
  public ResponseEntity<ErrorDto> handleConstraintViolationException(
      ConstraintViolationException exception) {
    logger.error(exception.getMessage(), exception);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(handleConstraintViolations(exception.getConstraintViolations()));
  }

  @ExceptionHandler
  public ResponseEntity<ErrorDto> handleMissingServletRequestParameterException(
      MissingServletRequestParameterException exception) {
    logger.error(exception.getMessage(), exception);
    ErrorDto error =
        new ErrorDto(
            List.of(
                new FaultDto(
                    INVALID_PARAMETER_TEMPLATE.formatted(exception.getParameterName()),
                    exception.getMessage())));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  private ErrorDto handleFieldErrors(MethodArgumentNotValidException exception) {
    List<FaultDto> faultDtos = new ArrayList<>();
    exception
        .getBindingResult()
        .getFieldErrors()
        .forEach(
            fieldError ->
                faultDtos.add(
                    new FaultDto(
                        INVALID_PARAMETER_TEMPLATE.formatted(fieldError.getField()),
                        fieldError.getDefaultMessage())));
    return new ErrorDto(faultDtos);
  }

  private ErrorDto handleConstraintViolations(Set<ConstraintViolation<?>> constraintViolations) {
    List<FaultDto> faultDtos = new ArrayList<>();

    constraintViolations.forEach(
        violation ->
            faultDtos.add(
                new FaultDto(
                    INVALID_PARAMETER_TEMPLATE.formatted(
                        getInvalidParameter(violation.getPropertyPath())),
                    violation.getMessage())));

    return new ErrorDto(faultDtos);
  }

  private String getInvalidParameter(Path propertyPath) {

    String[] pathParts = propertyPath.toString().split("\\.");
    return pathParts[pathParts.length - 1];
  }
}
