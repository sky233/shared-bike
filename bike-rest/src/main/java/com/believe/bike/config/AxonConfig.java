package com.believe.bike.config;

import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.messaging.interceptors.LoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import javax.validation.ValidatorFactory;

/**
 * <p> AxonConfig </p>
 *
 * @author Li Xingping
 */
@Configuration
public class AxonConfig {

  @Autowired
  private ValidatorFactory validatorFactory;

  @Autowired
  public void configure(@Qualifier("localSegment") SimpleCommandBus simpleCommandBus) {
    simpleCommandBus.registerDispatchInterceptor(new BeanValidationInterceptor<>(validatorFactory));
    simpleCommandBus.registerHandlerInterceptor(new LoggingInterceptor<>());
  }

}
