package com.init;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/11.
 */
@Configuration
@ComponentScan("com.Controller")
@EnableWebMvc
//@EnableScheduling
//@EnableTransactionManagement
//@EnableAspectJAutoProxy(proxyTargetClass = true)
@PropertySource("classpath:/jdbc.properties")
@MapperScan("com.dao")
public class WebAppConfig extends WebMvcConfigurationSupport {

    @Value("${jdbc.driver}")
    private String jdbc_driver;

    @Value("${jdbc.url}")
    private String jdbc_url;

    @Value("${jdbc.username}")
    private String jdbc_username;

    @Value("${jdbc.password}")
    private String jdbc_password;

    /**
     * 添加对静态资源的操作
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        log.info("增加静态资源");
        registry.addResourceHandler("/static/**").addResourceLocations(
                "/WEB-INF/static/");
    }

    /**
     * 需要显示声明 声明后可以调用拦截器的相关bean
     *
     * @return
     */
    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {

        RequestMappingHandlerAdapter requestMappingHandler = new RequestMappingHandlerAdapter();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        SimpleDateFormat simpleFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(simpleFormat);

        MappingJackson2HttpMessageConverter jscksonConverter = new MappingJackson2HttpMessageConverter();
        jscksonConverter.setObjectMapper(objectMapper);

        List<HttpMessageConverter<?>> jscksonList = new ArrayList<>();
        jscksonList.add(jscksonConverter);              //添加responseBody的返回值为json格式

        requestMappingHandler.setMessageConverters(jscksonList);
        return requestMappingHandler;
    }


    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(jdbc_driver);
            dataSource.setJdbcUrl(jdbc_url);
            dataSource.setUser(jdbc_username);
            dataSource.setPassword(jdbc_password);
            dataSource.setMinPoolSize(20);
            dataSource.setMaxPoolSize(60);
            dataSource.setInitialPoolSize(20);
            dataSource.setMaxIdleTime(60);
            dataSource.setAcquireIncrement(2);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        return dataSource;
    }



    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() {
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
//        configuration.setMapUnderscoreToCamelCase(true);
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource());
//        bean.setConfiguration(configuration);
//        bean.setTypeAliasesPackage("com.entity");

        //添加XML目录
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
//            bean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 初始化线程池
     *
     * @return
     */
//    @Bean
//    public ThreadPoolTaskExecutor taskExecutor() {
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        taskExecutor.setCorePoolSize(10);
//        taskExecutor.setMaxPoolSize(100);
//        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
//        return taskExecutor;
//    }

//    @Bean(name = "transactionManager")
//    public DataSourceTransactionManager makeDataSourceTransactionManager(DataSource dataSource) {
//        DataSourceTransactionManager manager = new DataSourceTransactionManager();
//        manager.setDataSource(dataSource);
//        return manager;
//    }

    //事务管理器
//    @Bean(name = "transactionManager")
//    public DataSourceTransactionManager dataSourceTransactionManager() {
//        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
//        dataSourceTransactionManager.setDataSource(dataSource());
//        return dataSourceTransactionManager;
//    }

//    @Bean(name="transactionInterceptor")
//    public TransactionInterceptor interceptor(){
//        TransactionInterceptor interceptor = new TransactionInterceptor();
//        interceptor.setTransactionManager(dataSourceTransactionManager());
//
//        Properties transactionAttributes = new Properties();
//        transactionAttributes.setProperty("save*", "PROPAGATION_REQUIRED");
//        transactionAttributes.setProperty("del*", "PROPAGATION_REQUIRED");
//        transactionAttributes.setProperty("update*", "PROPAGATION_REQUIRED");
//        transactionAttributes.setProperty("get*", "PROPAGATION_REQUIRED,readOnly");
//        transactionAttributes.setProperty("find*", "PROPAGATION_REQUIRED,readOnly");
//        transactionAttributes.setProperty("*", "PROPAGATION_REQUIRED");
//
//        interceptor.setTransactionAttributes(transactionAttributes);
//        return interceptor;
//    }


}
