//1st CONFIGURATION FILE @Configuration

package fr.epita.ATBeanNotation;

import fr.epita.services.data.ContactDAO;
import org.h2.Driver;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Configurable
public class DBSpringConfig {

    @Bean("selectQuery")
    public String select() {
        return "SELECT * FROM QUESTIONS";
    }

    @Bean("contactDAO")
    public ContactDAO getContactDAO(){
        return new ContactDAO();
    }

    @Bean("dataSource")
    public DataSource getDataSource() throws IOException {
        DriverManagerDataSource dmdc = new DriverManagerDataSource();
        dmdc.setDriverClassName(Driver.class.getName());

        dmdc.setUrl(propertyResolver("db.url"));
        dmdc.setUsername(propertyResolver("db.username"));
        dmdc.setPassword("db.password");
        return dmdc;
    }

    private Properties getProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(ResourceUtils.getFile("classpath:./config.properties")));
        return properties;
    }

    private String propertyResolver(String propertyKey) throws IOException {
        return getProperties().getProperty(propertyKey);
    }
}





//2nd CONFIGURATION FILE - @Configure & @Import the 1st File
package fr.epita.ATBeanNotation;

import fr.epita.ATBeanNotation.DBSpringConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Import(DBSpringConfig.class)
public class JPASpringConfig {

    @Inject
    @Named("dataSource")
    DataSource dataSource;

    @Bean("sessionFactory")
    public LocalSessionFactoryBean getSessionFactory(){
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(this.dataSource);
        //source of the contact class
        factoryBean.setPackagesToScan("src/main/java/fr/epita/AtBeanNotation");
        factoryBean.setHibernateProperties(new Properties());
        //for the moment we create an empty property
        return factoryBean;
    }


}





//TEST UNIT FOR THE SESSION FACTORY
package fr.epita.ATBeanNotationTest;

import fr.epita.ATBeanNotation.DBSpringConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DBSpringConfig.class})
public class DBSpringConfigTest {

    @Inject
    @Named("selectQuery")
    String select;

    @Inject
    @Named("dataSource")
    DataSource ds;


    @Test
    public void selectQueryTest(){
        //given

        //when

        //then
        Assert.assertNotNull(select);
    }
}





