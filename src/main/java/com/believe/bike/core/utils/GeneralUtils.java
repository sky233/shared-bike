package com.believe.bike.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.axonframework.common.IdentifierFactory;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GeneralUtils {

  public static String id() {
    return IdentifierFactory.getInstance().generateIdentifier();
  }

  public static String randomNumeric(int size) {
    return RandomStringUtils.randomNumeric(size);
  }

}
