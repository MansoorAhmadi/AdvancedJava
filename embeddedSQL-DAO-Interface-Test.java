//CONTACT CLASS

package fr.epita.contacts.datamodel;

/**
 * our main datamodel class to represent a contact
 */
public class Contact {
    private String name;
    private String lastName;
    private String companyName;
    private String address;
    private String city;
    private String county;
    private String state;
    private String zip;
    private String phone1;
    private String phone;
    private String email;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}




//CONTACT DAO

package fr.epita.contacts.services.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import fr.epita.contacts.datamodel.Contact;

@Repository("services.data.contactDAO")
public class ContactDAO implements IContactDAO {

    @Inject
    @Named("services.data.mainDS")
    DataSource ds;

    public void create(Contact contact) {
        try(Connection connection = ds.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into CONTACTS (NAME, LASTNAME) values (?, ?)");
            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getLastName());
            preparedStatement.execute();
        }catch(SQLException sqle){
            //TODO handle exception
            sqle.printStackTrace();
        }

    }
}
//We can have all CRUD operations



//IContact INTERFACE - for ABSTRACTION
@Repository("services.data.contactDAO")
public interface IContactDAO {
  
  void create(Contact contact);
  
  void update(Contact contact);
  
  void delete(Contact contact);
 
}



//CONFIGURATION class - Bean Notation
//@Bean - or the @Configuration class
package fr.epita.contacts.data.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Driver;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import fr.epita.contacts.services.data.ContactDAO;

@Configuration
@ComponentScan(basePackages = {"fr.epita.contacts.services.data"})
public class ConfigForUnitTest {


   @Bean
   public ContactDAO getContactDAO(){
       return new ContactDAO();
   }

    @Bean("services.data.mainDS")
    public DataSource getMainDS() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(Driver.class.getName());
        driverManagerDataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        driverManagerDataSource.setPassword("root");
        driverManagerDataSource.setUsername("root");
        return driverManagerDataSource;

    }

}



//CREATE-CONTACT.SQL
CREATE TABLE IF NOT EXISTS CONTACTS(
    name VARCHAR(255),
    lastName VARCHAR(255),
    companyName VARCHAR(255),
    address VARCHAR(255),
    city VARCHAR(255),
    county VARCHAR(255),
    state VARCHAR(255),
    zip VARCHAR(255),
    phone1 VARCHAR(255),
    phone VARCHAR(255),
    email VARCHAR(255)
)




//TestCONTACTDAO class
package fr.epita.contacts.data.tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.epita.contacts.datamodel.Contact;
import fr.epita.contacts.services.data.ContactDAO;
import fr.epita.contacts.services.data.IContactDAO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConfigForUnitTest.class)
public class TestContactDAO {

    //injecting datasource
    @Inject
    @Named("services.data.mainDS")
    private DataSource ds;

    //instance of ContactDAO through the interface
    @Inject
    @Named("services.data.contactDAO")
    private IContactDAO dao;

    //testing the embedded create-contact.sql query before the ContactDAO or the create() case
    @Before
    public void loadDatabase() throws IOException {
        String query = Files.readString(new File("src/main/resources/create-contacts.sql").toPath());
        try (Connection connection = ds.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCreate() {
        //given
        Contact contact = new Contact();
        contact.setName("Lenna");
        contact.setLastName("Paprocki");

        //when
        dao.create(contact);

        //then
        try (Connection connection = ds.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement("select name, lastname from contacts");
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String name = resultSet.getString("name");
            String lastName = resultSet.getString("lastname");
            Assert.assertEquals("Lenna", name);
            Assert.assertEquals("Paprocki", lastName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}




