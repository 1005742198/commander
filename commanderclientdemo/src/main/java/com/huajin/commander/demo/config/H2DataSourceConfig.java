package com.huajin.commander.demo.config;

import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;


//@Configuration
//@EnableJpaRepositories(basePackages = "com.huajin.commander.demo.repository")
public class H2DataSourceConfig {
	
	private static final String HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String HIBERNATE_SHOW_SQL = "hibernate.show.sql";

	@Resource
	private Environment env;
	
	@Bean(destroyMethod = "shutdown")
	@Primary
	public EmbeddedDatabase dataSource() {
	    return new EmbeddedDatabaseBuilder().
	            setType(EmbeddedDatabaseType.H2).
	            build();
	}
  
    @Bean(name="entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {  
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setPersistenceUnitName("default");
        factory.setDataSource(dataSource());  
        factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);  
        factory.setPackagesToScan("com.huajin.commander.demo.domain");  
        factory.setJpaProperties(hibernateProperties());  
        factory.afterPropertiesSet();  
        return factory;  
    }
  
    @Bean(name="transactionManager")
    @Primary
    public PlatformTransactionManager transactionManager() {  
        JpaTransactionManager manager = new JpaTransactionManager();  
        manager.setEntityManagerFactory(entityManagerFactory().getObject());  
        return manager;  
    }
    
	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put(HIBERNATE_DIALECT, "org.hibernate.dialect.H2Dialect");
		properties.put(HIBERNATE_SHOW_SQL, true);
		
		return properties;
	}
}
