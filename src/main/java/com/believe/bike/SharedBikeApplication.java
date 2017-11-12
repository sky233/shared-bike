package com.believe.bike;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@EntityScan({"org.axonframework", "com.believe.bike.query"})
@EnableJpaRepositories("com.believe.bike.query.*.repositories")
@SpringBootApplication
public class SharedBikeApplication {

  public static void main(String[] args) {
    SpringApplication.run(SharedBikeApplication.class, args);
  }
}
