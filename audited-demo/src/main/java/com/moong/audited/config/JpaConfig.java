package com.moong.audited.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

@Slf4j
@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@ConditionalOnProperty(name = "persistence.mode", havingValue = "hibernate")
public class JpaConfig {

    public final static String PERSISTENCE_UNIT_NAME = "defaultUnit";

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws SQLException {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
        em.setDataSource(dataSource());
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
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        hibernateProperties.setProperty("hibernate.format_sql", "true");
        hibernateProperties.setProperty("hibernate.use_sql_comments", "true");
        hibernateProperties.setProperty("org.hibernate.envers.audit_table_suffix", "_h");
        return hibernateProperties;
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2TcpServer() throws SQLException {
        log.info("Initializing H2 TCP Server");
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }

    /**
     * @apiNote h2 Datasource Config
     * Spring Boot에선 tomcat-jdbc를 기본 Datasource 로 제공
     * 하지만, 2.0 부턴 HikariCP가 Default
     * 현재 프로젝트는 2.2.1 이기 때문에 HikariCP를 사용.
     * spring.datasource로 값을 설정하기 보다는
     * spring.datasource.hikari로 수동/자동 구분없이 설정해야됌
     * @author moong
     */
    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource dataSource() throws SQLException {
        log.info("Initializing Hikari DataSource");
        h2TcpServer();
        return new HikariDataSource();
    }
}
