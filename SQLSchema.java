


import epita.fr.Quiz.Model.Question;
import epita.fr.Quiz.Service.Configuration;

import java.sql.*;

public class SQLConnectionTEST {
    public static void main(String[] args) {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/javaexam", "postgres",
                    "jasmine");

            String schema = connection.getSchema();
//            if (!"PUBLIC".equals(schema)){
//                throw new Exception("connection was not successful");
//            }
            System.out.println("Connection successful");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //CreateStatement
            String create = "CREATE TABLE IF NOT EXISTS QUESTION(ID IDENTITY PRIMARY KEY, TITLE VARCHAR(255), DIFFICULTY INT);";
            PreparedStatement createStatement = connection.prepareStatement(create);
            createStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            //InsertStatement
            String insert = "INSERT INTO QUESTION(TITLE, DIFFICULTY) VALUES (?,?)";
            PreparedStatement insertStatement = connection.prepareStatement(insert);
            insertStatement.setString(1, "What is a javadoc comment?");
            insertStatement.setInt(2, 2);
            insertStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            //SelectStatement
            String select = "SELECT ID, TITLE, DIFFICULTY FROM QUESTION";
            PreparedStatement selectStatement = connection.prepareStatement(select);
            ResultSet resultSet = selectStatement.executeQuery();

            //While Loop
            while (resultSet.next()){
                Question question = new Question();
                question.setId(resultSet.getInt("ID"));
                question.setQuestion(resultSet.getString("TITLE"));
                question.setDifficulty(resultSet.getInt("DIFFICULTY"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
