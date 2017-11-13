package com.believe.bike.api.user;

import com.believe.bike.core.utils.GeneralUtils;
import lombok.Getter;
import org.axonframework.common.Assert;
import org.hibernate.validator.constraints.NotBlank;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Getter
public class UserId {

  @NotBlank
  protected final String identifier;

  public UserId() {
    this.identifier = GeneralUtils.id();
  }

  public UserId(String identifier) {
    Assert.notNull(identifier, () -> "Identifier may not be null");
    this.identifier = identifier;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserId that = (UserId) o;
    return identifier.equals(that.identifier);
  }

  @Override
  public int hashCode() {
    return this.identifier.hashCode();
  }

  @Override
  public String toString() {
    return this.identifier;
  }
}
