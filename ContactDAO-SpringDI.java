//DAO with Spring Dependency Injection 'datasource'

package fr.epita.DataModel;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContactDAO {

    @Inject
    @Named("mainDS")
    DataSource dataSource;

    public void create(Contact newContact){
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement createStatement = connection.prepareStatement("");
            createStatement.setString(1, newContact.getFirst_name());
            createStatement.setString(2, newContact.getLast_name());
            createStatement.setString(3, newContact.getCompany_name());
            createStatement.setString(4, newContact.getAddress());
            createStatement.setString(5, newContact.getCity());
            createStatement.setString(6, newContact.getCounty());
            createStatement.setString(7, newContact.getState());
            createStatement.setString(8, newContact.getZip());
            createStatement.setString(9, newContact.getPhone1());
            createStatement.setString(10, newContact.getPhone());
            createStatement.setString(11, newContact.getEmail());
            createStatement.executeUpdate();
            createStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
