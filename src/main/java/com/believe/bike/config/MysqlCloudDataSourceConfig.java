package com.believe.bike.config;

import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Configuration
@Profile("cloud")
public class MysqlCloudDataSourceConfig extends AbstractCloudConfig {
  // cf create-service cleardb spark shared-bike-db
  // cf bind-service shared-bike shared-bike-db

  @Bean
  public DataSource dataSource() {
    return connectionFactory().dataSource();
  }

}
