package com.believe.bike.core.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorCode {

  public static final int VIOLATION_CONSTRAINT = 0XFFFE;
  public static final int UNKNOWN_ERROR = 0XFFFF;

}
