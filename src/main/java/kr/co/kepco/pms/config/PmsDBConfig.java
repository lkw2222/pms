package kr.co.kepco.pms.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/database.properties")
@MapperScan(basePackages = "kr.co.kepco.pms.main.*.mapper", sqlSessionFactoryRef = "pmsSqlSessionFactory")
@EnableTransactionManagement
public class PmsDBConfig {

    private final ApplicationContext applicationContext;

    public PmsDBConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Value("${spring.pms.datasource.mapper-locations}")
    private String mapperLocation;

    @Value("spring.common.datasource.mybatis-config")
    private String configPath;

    @Bean(name="pmsDatasource")
    @ConfigurationProperties("spring.pms.datasource")
    public DataSource pmsDatasource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name="pmsSqlSessionFactory")
    public SqlSessionFactory pmsSqlSessionFactory(@Qualifier("pmsDatasource") DataSource pmsDatasource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(pmsDatasource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResource(mapperLocation));

        Resource configLocation = new PathMatchingResourcePatternResolver().getResource(configPath);
        sqlSessionFactoryBean.setConfigLocation(configLocation);

        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public TransactionManager pmsTransactionManager() {
        return new DataSourceTransactionManager(pmsDatasource());
    }
}
