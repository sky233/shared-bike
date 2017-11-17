package com.believe.bike.core.exception;

import lombok.Getter;

/**
 * <p> The Domain object throws </p>
 *
 * @author Li Xingping
 */
@Getter
public class DomainException extends RuntimeException {
  private final int code;
  private String messageCode;
  private Object[] args;

  public DomainException(int code) {
    super();
    this.code = code;
  }

  public DomainException(int code, String messageCode, Object... args) {
    super(messageCode);
    this.code = code;
    this.messageCode = messageCode;
    this.args = args;
  }

  public DomainException(Throwable cause, int code, String messageCode, Object... args) {
    super(messageCode, cause);
    this.code = code;
    this.messageCode = messageCode;
    this.args = args;
  }

  public static DomainException unKnown() {
    return new DomainException(ErrorCode.UNKNOWN_ERROR, "Internal Error.");
  }

}
