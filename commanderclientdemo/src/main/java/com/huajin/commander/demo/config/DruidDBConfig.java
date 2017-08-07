package com.huajin.commander.demo.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
//import com.huajin.baymax.encrypt.SymmetricEncrypt;

/**
 * Druid DataSource with MySQL5.
 * 
 * @author bo.yang
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.huajin.commander.demo.repository")
public class DruidDBConfig {
	
	private Logger logger = LoggerFactory.getLogger(DruidDBConfig.class);

	private static final String HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String HIBERNATE_SHOW_SQL = "hibernate.show.sql";

	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private String password;
	@Value("${spring.datasource.driverClassName}")
	private String driverClassName;
	@Value("${spring.datasource.initialSize}")
	private int initialSize;
	@Value("${spring.datasource.minIdle}")
	private int minIdle;
	@Value("${spring.datasource.maxActive}")
	private int maxActive;
	@Value("${spring.datasource.maxWait}")
	private int maxWait;
	@Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;
	@Value("${spring.datasource.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;
	@Value("${spring.datasource.validationQuery}")
	private String validationQuery;
	@Value("${spring.datasource.validationQueryTimeout}")
	private int validationQueryTimeout;
	@Value("${spring.datasource.testWhileIdle}")
	private boolean testWhileIdle;
	@Value("${spring.datasource.testOnBorrow}")
	private boolean testOnBorrow;
	@Value("${spring.datasource.testOnReturn}")
	private boolean testOnReturn;
	@Value("${spring.datasource.poolPreparedStatements}")
	private boolean poolPreparedStatements;
	@Value("${spring.datasource.filters}")
	private String filters;

	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public int getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public int getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public int getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public int getValidationQueryTimeout() {
		return validationQueryTimeout;
	}

	public void setValidationQueryTimeout(int validationQueryTimeout) {
		this.validationQueryTimeout = validationQueryTimeout;
	}

	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public boolean isPoolPreparedStatements() {
		return poolPreparedStatements;
	}

	public void setPoolPreparedStatements(boolean poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}
	
	@Bean
	@Primary
	public DataSource dataSource() {
		
		DruidDataSource datasource = new DruidDataSource();  
        
        datasource.setUrl(url);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);  
          
        datasource.setInitialSize(initialSize);  
        datasource.setMinIdle(minIdle);  
        datasource.setMaxActive(maxActive);  
        datasource.setMaxWait(maxWait);  
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);  
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);  
        datasource.setValidationQuery(validationQuery);  
        datasource.setTestWhileIdle(testWhileIdle);  
        datasource.setTestOnBorrow(testOnBorrow);  
        datasource.setTestOnReturn(testOnReturn);  
        datasource.setPoolPreparedStatements(poolPreparedStatements);  
        try {  
            datasource.setFilters(filters);  
        } catch (SQLException e) {  
            logger.error("druid configuration initialization filter", e);  
        }  
          
        return datasource;  
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
		properties.put(HIBERNATE_DIALECT, "org.hibernate.dialect.MySQL5Dialect");
		properties.put(HIBERNATE_SHOW_SQL, true);
		
		return properties;
	}
}
