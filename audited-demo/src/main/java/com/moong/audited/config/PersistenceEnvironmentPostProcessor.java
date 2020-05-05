package com.moong.audited.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * https://www.baeldung.com/spring-boot-environmentpostprocessor
 * @author gmoon
 * */
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
public class PersistenceEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final String HIBERNATE_ENVIRONMENT_PROPERTY_SOURCE_NAME = "hibernate-persistence";

    private static final String PREFIX = "hibernate";

    private static final String SHOW_SQL_MODE = "show_sql";

    private static final String FORMAT_SQL_MODE = "format_sql";

    private static final String SHOW_SQL_MODE_DEFAULT_VALUE = "true";

    private static final String FORMAT_SQL_MODE_DEFAULT_VALUE = "true";

    private List<String> names = Arrays.asList(PREFIX);

    private static Map<String, Object> defaults = new LinkedHashMap<>();

    static {
        defaults.put(SHOW_SQL_MODE, SHOW_SQL_MODE_DEFAULT_VALUE);
        defaults.put(FORMAT_SQL_MODE, FORMAT_SQL_MODE_DEFAULT_VALUE);
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        PropertySource<?> system = environment.getPropertySources()
                .get(HIBERNATE_ENVIRONMENT_PROPERTY_SOURCE_NAME);

        Map<String, Object> prefixed = new LinkedHashMap<>();

        if (!hasOurHibernateProperties(system)) {
            // Baeldung-internal code so this doesn't break other examples
            log.warn("System environment variables [calculation_mode,gross_calculation_tax_rate] not detected, fallback to default value [calcuation_mode={},gross_calcuation_tax_rate={}]",
                    SHOW_SQL_MODE, FORMAT_SQL_MODE);
            prefixed = names.stream()
                    .collect(Collectors.toMap(this::rename, this::getDefaultValue));
            environment.getPropertySources()
                    .addAfter(HIBERNATE_ENVIRONMENT_PROPERTY_SOURCE_NAME, new MapPropertySource("prefixer", prefixed));
            return;
        }
        prefixed = names.stream().collect(Collectors.toMap(this::rename, system::getProperty));
        environment.getPropertySources().addAfter(HIBERNATE_ENVIRONMENT_PROPERTY_SOURCE_NAME, new MapPropertySource("prefixer", prefixed));
    }

    private String rename(String key) {
        return PREFIX + key.replaceAll("\\_", ".");
    }

    private Object getDefaultValue(String key) {
        return defaults.get(key);
    }

    private boolean hasOurHibernateProperties(PropertySource<?> system) {
        return system.containsProperty(PREFIX);
    }
}
