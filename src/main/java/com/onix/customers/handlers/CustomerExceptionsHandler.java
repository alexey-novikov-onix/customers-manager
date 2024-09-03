package com.onix.customers.handlers;

import com.onix.customers.enumerations.RequestAction;
import com.onix.customers.enumerations.RequestStatus;
import com.onix.customers.exceptions.CustomerNotFoundException;
import com.onix.customers.responses.ErrorResponse;
import com.onix.customers.responses.ValidationError;
import com.onix.customers.services.AuditEntryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class CustomerExceptionsHandler {

    private final AuditEntryService auditEntryService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<List<ValidationError>>> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException exception, final HttpServletRequest request
    ) {
        logAudit(request);

        final var data = exception.getAllErrors().stream()
                .map(error -> new ValidationError(
                        ((FieldError) error).getField(),
                        StringUtils.capitalize(Objects.requireNonNull(error.getDefaultMessage())))
                )
                .sorted(Comparator.comparing(ValidationError::field))
                .toList();

        return new ResponseEntity<>(
                new ErrorResponse<>(
                        "Validation failed",
                        data
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({
            MissingServletRequestPartException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            BindException.class
    })
    public ResponseEntity<ErrorResponse<String>> handleEntityNotFoundException(
            final Exception exception,
            final HttpServletRequest request
    ) {
        logAudit(request);

        return new ResponseEntity<>(
                new ErrorResponse<>(exception.getMessage(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleEntityNotFoundException(
            final CustomerNotFoundException exception,
            final HttpServletRequest request
    ) {
        logAudit(request);

        return new ResponseEntity<>(
                new ErrorResponse<>(exception.getMessage(), null),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<String>> handleExceptions(
            final Exception exception, final HttpServletRequest request
    ) {
        logAudit(request);

        log.error("Exception handler", exception);

        return new ResponseEntity<>(
                new ErrorResponse<>("An unexpected error occurred", null),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private void logAudit(final HttpServletRequest request) {
        final var action = determineActionFromHttpMethod(request.getMethod());
        if (action != null) {
            auditEntryService.log(action, null, getCustomerBody(request), RequestStatus.FAILED);
        }
    }

    private RequestAction determineActionFromHttpMethod(final String httpMethod) {
        return switch (httpMethod) {
            case "POST" -> RequestAction.CREATE;
            case "PUT" -> RequestAction.UPDATE;
            case "DELETE" -> RequestAction.DELETE;
            default -> null;
        };
    }

    private String getCustomerBody(final HttpServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper wrapper) {
            return new String(wrapper.getContentAsByteArray());
        }

        return null;
    }

}
