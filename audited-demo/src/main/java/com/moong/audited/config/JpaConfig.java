package com.moong.audited.config;

import com.moong.audited.config.properties.HibernateProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Optional;
import java.util.Properties;

@Slf4j
@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@Import( { HibernateJpaAutoConfiguration.class })
@RequiredArgsConstructor
public class JpaConfig {

    public final static String PERSISTENCE_UNIT_NAME = "defaultUnit";

    private final HibernateProperties hibernateProperties;

    private final DataSource dataSource;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
        em.setDataSource(dataSource);
        em.setPackagesToScan(new String[]{ "com.moong.audited.**.domain" });
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(hibernateProperties());
        return em;
    }

    @Bean
    public JpaTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("gmoon92.github.io");
    }

    private Properties hibernateProperties() {
        log.info("hibernateProperties : {}", hibernateProperties);
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", hibernateProperties.getHbm2ddlAuto());
        properties.setProperty("hibernate.dialect", hibernateProperties.getDialect());
        properties.setProperty("hibernate.show_sql", hibernateProperties.getShowSql());
        properties.setProperty("hibernate.format_sql", hibernateProperties.getFormatSql());
        properties.setProperty("hibernate.use_sql_comments", hibernateProperties.getUseSqlComments());
        properties.setProperty("org.hibernate.envers.audit_table_suffix", hibernateProperties.getAuditTableSuffix());
        return properties;
    }
}
