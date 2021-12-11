//Testing the Contact-DAO
//Injecting the DataSource through ApplicationContext.xml

package fr.epita.DAOTest;

import fr.epita.DataModel.Question;
import fr.epita.DataModel.ContactDAO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TestDAO {

    @Inject
    @Named("mainDS")
    DataSource dataSource;

    @Inject
    ContactDAO contactDAO;
    
    //Testing the DataSource
    @Test
    public void testDataSource(){
        
        //given
        String schema = null;
        try (Connection connection = ds.getConnection();) {
            schema = connection.getSchema();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(schema);
        
        //when

        //then
        Assert.assertEquals("PUBLIC", schema);
    }

    @Test
    public void testDAO() {
        
        //give
        Question contact = new Question();
        contact.setFirst_name("What is your name?");
        
        //when
        contactDAO.create(contact);
        
        //then
        Assert.assertNotNull(contactDAO);
    }
}
