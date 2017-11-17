package com.believe.bike.rest;

import com.believe.bike.core.exception.DomainException;
import com.believe.bike.core.help.MessagesHelper;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.model.ConcurrencyException;
import org.axonframework.messaging.interceptors.JSR303ViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

import static com.believe.bike.core.exception.ErrorCode.VIOLATION_CONSTRAINT;
import static com.believe.bike.core.exception.ErrorMessage.of;
import static com.believe.bike.core.exception.ErrorMessage.unKnown;

/**
 * <p> RestResponseEntityExceptionHandler </p>
 *
 * @author Li Xingping
 */
@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  private final MessagesHelper messagesHelper;

  @Autowired
  public RestResponseEntityExceptionHandler(MessagesHelper messagesHelper) {
    super();
    this.messagesHelper = messagesHelper;
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status,
                                                                final WebRequest request) {
    final String bodyOfResponse = "HttpMessageNotReadableException";
    log.error(bodyOfResponse, ex);
    return handleExceptionInternal(ex, unKnown(bodyOfResponse), headers, HttpStatus.BAD_REQUEST, request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status,
                                                                final WebRequest request) {
    final String bodyOfResponse = getMethodArgumentNotValidMessages(ex);
    log.error(bodyOfResponse, ex);
    return handleExceptionInternal(ex, of(VIOLATION_CONSTRAINT, bodyOfResponse), headers, HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler({CommandExecutionException.class})
  protected ResponseEntity<Object> handleCommandExecution(final RuntimeException cex, final WebRequest request) {
    final String bodyOfResponse = "CommandExecutionException";
    if (null != cex.getCause()) {
      log.error("CAUSED BY: {} {}", cex.getCause().getClass().getName(), cex.getCause().getMessage());
      if (cex.getCause() instanceof ConcurrencyException) {
        return handleExceptionInternal(cex, unKnown(bodyOfResponse + " - Concurrency issue"), new HttpHeaders(), HttpStatus.CONFLICT, request);
      }
    }
    return handleExceptionInternal(cex, unKnown(bodyOfResponse), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler({InvalidDataAccessApiUsageException.class, DataAccessException.class})
  protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
    final String bodyOfResponse = "DataAccessException";
    log.error(bodyOfResponse, ex);
    return handleExceptionInternal(ex, unKnown(bodyOfResponse), new HttpHeaders(), HttpStatus.CONFLICT, request);
  }

  @ExceptionHandler({DataIntegrityViolationException.class})
  public ResponseEntity<Object> handleBadRequest(final DataIntegrityViolationException ex, final WebRequest request) {
    final String bodyOfResponse = "DataIntegrityViolationException";
    log.error(bodyOfResponse, ex);
    return handleExceptionInternal(ex, of(VIOLATION_CONSTRAINT, bodyOfResponse), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class})
  public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
    final String bodyOfResponse = "Internal Error";
    log.error(bodyOfResponse, ex);
    return handleExceptionInternal(ex, unKnown(bodyOfResponse), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  @ExceptionHandler({TransactionSystemException.class})
  public ResponseEntity<Object> handleConstraintViolation(Exception ex, WebRequest request) {
    Throwable cause = Throwables.getRootCause(ex);
    String bodyOfResponse = "TransactionSystemException";
    log.error(bodyOfResponse, ex);
    if (cause instanceof ConstraintViolationException) {
      Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) cause).getConstraintViolations();
      bodyOfResponse = getConstraintViolationsMessages(constraintViolations);
      return ResponseEntity.badRequest().body(of(VIOLATION_CONSTRAINT, bodyOfResponse));
    }
    return handleExceptionInternal(ex, unKnown(bodyOfResponse), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  @ExceptionHandler({JSR303ViolationException.class})
  public ResponseEntity<Object> handleValidation(final JSR303ViolationException ex, final WebRequest request) {
    final String bodyOfResponse = getJSR303ViolationMessages(ex.getViolations());
    log.error("Validation error", ex);
    return ResponseEntity.badRequest().body(of(VIOLATION_CONSTRAINT, bodyOfResponse));
  }

  @ExceptionHandler({ConstraintViolationException.class})
  public ResponseEntity<Object> handleValidation(final ConstraintViolationException ex, final WebRequest request) {
    final String bodyOfResponse = getConstraintViolationsMessages(ex.getConstraintViolations());
    log.error("Validation error", ex);
    return ResponseEntity.badRequest().body(of(VIOLATION_CONSTRAINT, bodyOfResponse));
  }

  @ExceptionHandler({DomainException.class})
  public ResponseEntity<Object> handleDomainException(final DomainException ex, final WebRequest request) {
    log.error("Domain execution error", ex);
    String bodyOfResponse = null;
    if (StringUtils.isNotBlank(ex.getMessageCode())) {
      bodyOfResponse = messagesHelper.get(ex.getMessageCode(), ex.getArgs());
    }
    return ResponseEntity.badRequest().body(of(ex.getCode(), bodyOfResponse));
  }

  private String getConstraintViolationsMessages(final Set<ConstraintViolation<?>> violations) {
    StringBuilder sb = new StringBuilder();
    for (ConstraintViolation error : violations) {
      // todo 优化code提取算法
      sb.append("[").append(error.getPropertyPath().toString()).append(messagesHelper.get(StringUtils.remove(StringUtils.remove(error.getMessageTemplate(), '}'), '{'))).append("] ");
    }
    return sb.toString();
  }

  private String getJSR303ViolationMessages(final Set<ConstraintViolation<Object>> violations) {
    StringBuilder sb = new StringBuilder();
    for (ConstraintViolation error : violations) {
      sb.append("[").append(error.getPropertyPath().toString()).append(error.getMessage()).append("] ");
    }
    return sb.toString();
  }

  private String getMethodArgumentNotValidMessages(final MethodArgumentNotValidException ex) {
    StringBuilder sb = new StringBuilder();
    for (ObjectError error : ex.getBindingResult().getAllErrors()) {
      sb.append("[").append(((FieldError) error).getField()).append(error.getDefaultMessage()).append("] ");
    }
    return sb.toString();
  }

}