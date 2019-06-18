package com.eaads.phosphor_service.repository;

import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.eaads.phosphor_service.utils.SSMParams;

@Configuration
@EnableTransactionManagement
public class PersistenceConfig{
 
   @Bean
   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
      LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
      em.setDataSource(dataSource());
      em.setPackagesToScan("com.eaads.phosphor_service.models", 
    		  "com.eaads.phosphor_service.lambdas");
      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
      em.setJpaVendorAdapter(vendorAdapter);
      em.setJpaProperties(additionalProperties());
 
      return em;
   }
   
   
   @Bean
   public DataSource dataSource() {
	   SSMParams ssm = new SSMParams();
       DriverManagerDataSource dataSource = new DriverManagerDataSource();
       dataSource.setDriverClassName("org.postgresql.Driver");
       dataSource.setUrl(String.format("jdbc:postgresql://%s:%s/%s",
		        ssm.getDbServer(),
		        ssm.getDbPort(),
		        ssm.getDbName()));
       dataSource.setUsername( ssm.getDbUser() );
       dataSource.setPassword( ssm.getDbPass());
       return dataSource;
   }
   
   Properties additionalProperties() {
	    Properties properties = new Properties();
	    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
	    properties.setProperty("connection.pool_size", "2");
	    properties.setProperty("show_sql", "true");
	    return properties;
   }

}