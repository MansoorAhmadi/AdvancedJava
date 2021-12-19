//INSURANCE CLASS

package fr.epita.DataModel;

public class Insurances {

    private int id;
    private String name;

    @Override
    public String toString() {
        return "Insurances{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}





//INSURANCE DAO
package fr.epita.data;

import fr.epita.DataModel.Insurances;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsurancesDAO extends GenericDAO {

    //What are the 4 operations that we normally have in a DAO
    //ISUD - Insert, Select, Update, Delete


//    private Connection getConnection() throws SQLException {
//        return DriverManager.getConnection("","","");
//    }
    //WE MOVED THE CONNECTION TO THE GENERIC CLASS
    //WE MADE THE CONNECTION AS PROTECTED FROM PRIVATE BECAUSE WE WILL USE THE INHERITANCE


//    public void Insert(Insurances newInsurance) {
//        try {
//            Connection connection = getConnection();
//            try {
//                String insert = "INSERT INTO INSURANCES(ID INT NOT NULL, NAME VARCHAR(255) NOT NULL) VALUES(?,?);";
//                PreparedStatement insertStatement = connection.prepareStatement(insert);
//                insertStatement.setInt(1, newInsurance.getId());
//                insertStatement.setString(2, newInsurance.getName());
//                insertStatement.executeUpdate();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        } catch (SQLException sqlException) {
//                sqlException.printStackTrace();
//        }
//    }


//IMPLEMENTING THE GENERIC DAO
    @Override
    protected PreparedStatement getCreatePreparedStatement(Insurances newInsurance, Connection connection) {
        try {
            String insert = "INSERT INTO INSURANCES(ID INT NOT NULL, NAME VARCHAR(255) NOT NULL) VALUES(?,?);";
            PreparedStatement insertStatement = connection.prepareStatement(insert);
            insertStatement.setInt(1, newInsurance.getId());
            insertStatement.setString(2, newInsurance.getName());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}





//GENERIC INSURANCESDAO
package fr.epita.data;

import fr.epita.DataModel.Insurances;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class GenericDAO {

    @Inject
    @Named("mainDS")
    DataSource dataSource;

    //OPTION #2
    //WE MOVED THE CONNECTION FROM INSURANCESDAO to GENERIC
    protected Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void Insert(Insurances newInsurance) {
        try {
            Connection connection = getConnection();
            PreparedStatement insertStatement = getCreatePreparedStatement(newInsurance, connection);
            insertStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    //We extracted the tru that includes the insert query and prepareStatements into a protected abstract method.
    //We removed the insert query and prepareStatements from the abstracted method.
    //3rd step is that InsurancesDAO will now extend the GenericDAO class and inherit the protected methods
    protected abstract PreparedStatement getCreatePreparedStatement(Insurances newInsurance, Connection connection);
}






//TEST UNIT FOR INSERT OPERATION
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
    @Named("insuranceDAO")
    InsurancesDAO insurancesDAO;

    @Test
    public void testInsuranceDAO() {

        //give
        Insurances insurances = new Insurances();
        insurances.setName("What is your name?");

        //when
        insurancesDAO.Insert(insurances);

        //then
        Assert.assertNotNull(insurancesDAO);
    }
}









