

package Domain1.dataModelDomain3.DAO;

import Domain1.dataModelDomain1.patient.Patient;

import java.lang.module.Configuration;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    private Connection getConnection() {

//        Configuration configuration = Configuration.getInstance();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1","postgres","postgres");

            //Create H2 statement
            String create = "CREATE TABLE PATIENTS (ID IDENTITY, pat_num_HC INT NOT NULL, \n" +
                    "   pat_lastname VARCHAR(50) NOT NULL, \n" +
                    " pat_firstname VARCHAR(100) NOT NULL, \n" +
                    "   pat_address VARCHAR(200) NOT NULL, \n" +
                    "   pat_tel VARCHAR(50) NOT NULL, \n" +
                    "   pat_insurance_id VARCHAR(250) NOT NULL, \n" +
                    "   pat_sub_date DATE \n" +
                    ");";

            //Prepare Statement API
            PreparedStatement createStatement = connection.prepareStatement(create);
            createStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println("Connection and table created successfully!");
        return connection;
    }

    public void insert(Patient newPatient) {
        try (Connection connection = getConnection()) {
            String insert = "INSERT INTO PATIENTS (pat_num_HC, pat_lastname, pat_firstname, pat_address, pat_tel," +
                    "pat_insurance_id, pat_sub_date) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement insertStatement = connection.prepareStatement(insert);
            insertStatement.setString(1, newPatient.getPat_num_HC());
            insertStatement.setString(2, newPatient.getPat_lastname());
            insertStatement.setString(3, newPatient.getPat_firstname());
            insertStatement.setString(4, newPatient.getPat_address());
            insertStatement.setString(5, newPatient.getPat_tel());
            insertStatement.setString(6, newPatient.getPat_insurance_id());
//            insertStatement.setTime(7, newPatient.getPat_sub_date());
            insertStatement.execute();

//            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
//            CreationException creationException = new CreationException();
//            creationException.initCause(e);
//            throw creationException;
        }
        System.out.println("Insertion successful!");
    }

    public List<Patient> select() {

        //To insert all the patients into myList - to add
        List<Patient> myList = new ArrayList<>();

        //Passing connection as a parameter
        try (Connection connection = getConnection()) {
            String select = "SELECT pat_num_HC, pat_lastname, pat_firstname, pat_address, pat_tel,\" +\n" +
                    "                    \"pat_insurance_id, pat_sub_date FROM PATIENTS";

            String selectTask = "SELECT pat_lastname, pat_firstname FROM PATIENTS";
            PreparedStatement selectStatement = connection.prepareStatement(selectTask);

            //Since it has a return value
            ResultSet resultSet = selectStatement.executeQuery();

            //Looping through each field and using getters and setters
            while (resultSet.next()) {
                Patient patient = new Patient();
                patient.setPat_num_HC(resultSet.getString("PAT_NUM_HC"));
                patient.setPat_lastname(resultSet.getString("PAT_LASTNAME"));
                patient.setPat_firstname(resultSet.getString("PAT_FIRSTNAME"));
                patient.setPat_address(resultSet.getString("PAT_ADDRESS"));
                patient.setPat_tel(resultSet.getString("PAT_TEL"));
                patient.setPat_insurance_id(resultSet.getString("PAT_INSURANCE_ID"));
                patient.setPat_sub_date(resultSet.getDate("PAT_SUB_DATE"));
                myList.add(patient);
            }
//            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Selection successful!");
        return myList;
    }

    public void delete() {
        try (Connection connection = getConnection()) {
            String delete = "DELETE FROM PATIENTS WHERE pat_firstname = \" + 'Mansoor' + \"";
            PreparedStatement deleteStatement = connection.prepareStatement(delete);
            deleteStatement.execute();
            System.out.println("Deletion successful!");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        try (Connection connection = getConnection()) {
            String update = "UPDATE PATIENT" +
                    "SET PAT_FIRSTNAME ='MANSOOR' WHERE par_lastname = 'Martin'";
            PreparedStatement updateStatement = connection.prepareStatement(update);
            updateStatement.execute();
            System.out.println("Update successful!");
            //connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
