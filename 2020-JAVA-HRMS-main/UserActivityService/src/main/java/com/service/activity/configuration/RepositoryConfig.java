package com.service.activity.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@Configuration
public class RepositoryConfig {
	
    @Value("${spring.datasource.driver-class-name}")     
    private String driverClassName;
    
    @Value("${spring.datasource.url}")                 
    private String url;
    
    @Value("${spring.datasource.username}")             
    private String username;
    
    @Value("${spring.datasource.password}")             
    private String password;
    
    @Value("${hibernate.dialect}")         
    private String hibernateDialect;
    
    @Value("${hibernate.show_sql}")    
    private String hibernateShowSql;
    
    @Value("${hibernate.hbm2ddl.auto}") 
    private String hibernateHbm2ddlAuto;
        
    @Bean()    
    public DataSource getDataSource()
    {
        DriverManagerDataSource ds = new DriverManagerDataSource();        
        ds.setDriverClassName(driverClassName);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);        
        return ds;
    }
    
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory)
    {
        HibernateTransactionManager htm = new HibernateTransactionManager();
        htm.setSessionFactory(sessionFactory);
        return htm;
    }
    
    @Bean
    @Autowired
    public HibernateTemplate getHibernateTemplate(SessionFactory sessionFactory)
    {
        HibernateTemplate hibernateTemplate = new HibernateTemplate(sessionFactory);
        return hibernateTemplate;
    }
        
    @Bean
    public LocalSessionFactoryBean  getSessionFactory()
    {
    	LocalSessionFactoryBean  sessionFactory = new LocalSessionFactoryBean 	();
        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setHibernateProperties(getHibernateProperties());        
        sessionFactory.setPackagesToScan(new String[]{"com.service"});
        return sessionFactory;
    }

    @Bean
    public Properties getHibernateProperties()
    {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", hibernateDialect);
        properties.put("hibernate.show_sql", hibernateShowSql);
        properties.put("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
        
        return properties;
    }

}
