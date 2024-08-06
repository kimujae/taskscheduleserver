//package com.yeonieum.scheduledTeskserver.config;
//import jakarta.persistence.EntityManagerFactory;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.util.Properties;
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "com.yeonieum.scheduledTeskserver.*",
//entityManagerFactoryRef = "entityManagerFactory",
//transactionManagerRef = "jpaTransactionManager") // 패키지 경로를 실제 경로로 수정하세요
//public class JpaConfig {
//
//    @Value("${spring.datasource.url}")
//    private String url;
//    @Value("${spring.datasource.username}")
//    private String username;
//    @Value("${spring.datasource.password}")
//    private String password;
//    @Value("${spring.datasource.driver-class-name}")
//    private String driverClassName;
//
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource dataSource() {
//        return DataSourceBuilder.create()
//                .url(url)
//                .password(password)
//                .username(username)
//                .driverClassName(driverClassName)
//                .build();
//    }
//
//
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
//        LocalContainerEntityManagerFactoryBean emfb =builder.dataSource(this.dataSource())
//                .packages("com.yeonieum.scheduledTeskserver.*") // 패키지 경로를 실제 경로로 수정하세요
//                .build();
//        emfb.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//
//        // Hibernate 속성 설정
//        Properties jpaProperties = new Properties();
//        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
//        jpaProperties.put("sql.show", "true");
//        emfb.setJpaProperties(jpaProperties);
//
//        return emfb;
//
//
//    }
//
//    @Bean(name = "jpaTransactionManager")
//    public PlatformTransactionManager jpaTransactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
//}