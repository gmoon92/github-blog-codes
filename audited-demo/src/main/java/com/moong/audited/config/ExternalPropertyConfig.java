package com.moong.audited.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

/**
 * https://www.baeldung.com/spring-boot-environmentpostprocessor#Spring_Environment
 * https://github.com/eugenp/tutorials/blob/master/spring-boot-modules/spring-boot-environment/src/main/java/com/baeldung/environmentpostprocessor/PriceCalculationEnvironmentPostProcessor.java
 *
 * @author gmoon
 */
@Configuration
public class ExternalPropertyConfig {

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();
        properties.setLocations(new FileSystemResource("src/main/resources/external/hibernate-persistence.properties"));
        properties.setIgnoreResourceNotFound(false);
        return properties;
    }
}
