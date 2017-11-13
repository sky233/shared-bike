package com.believe.bike.core.exception;

import lombok.Getter;

/**
 * <p> The Domain object throws </p>
 *
 * @author Li Xingping
 */
@Getter
public class DomainException extends RuntimeException {

  public DomainException() {
    super();
  }

  public DomainException(String message) {
    super(message);
  }

  public DomainException(String message, Throwable cause) {
    super(message, cause);
  }
}
