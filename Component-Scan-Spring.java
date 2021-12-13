//Questions Class

package fr.epita.quizDAOModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="QUESTIONS")
public class Questions {

    @Id
    @Column(name = "title")
    private String questionTitle;

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }
}



//QuestionsDAO Class
package fr.epita.quizDAOModel;

import fr.epita.DataModel.Question;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Service("questionJdbcDAO") //we can name our service here, or we can continue without a name as well
public class QuestionsDAO {

    DataSource dataSource;

    public void create(Questions questions) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO QUESTIONS (title) values (?)");
            preparedStatement.setString(1, questions.getQuestionTitle());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




//Test Class
package fr.epita.Spring;

import fr.epita.quizDAOModel.Questions;
import fr.epita.quizDAOModel.QuestionsDAO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TestSpring {

   //Injecting DataSource - Bean - xml file
    @Inject
    @Named("mainDS")
    DataSource ds;

  //Injected the QuestionsDAO by @Service and specified at component-scan inside the xml file
    @Inject
    @Named("questionJdbcDAO")
    QuestionsDAO questionsDAO;

  
//Test Unit
    @Test
    public void testDatasource() {
        //given - a proper spring configuration

        //when - injection occurs when junit starts the test

        //then
        Assert.assertNotNull(ds);
        try {
            Connection connection = ds.getConnection();

            String schema = connection.getSchema();
            System.out.println(schema);
            Assert.assertEquals("PUBLIC",
                    schema);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

//Test Unit
    @Test
    public void testQuestionsDAO() {
        //given
        Questions questions = new Questions();
        
        //when
        questions.setQuestionTitle("What is Spring?");
        
        //then
        questionsDAO.create(questions);
    }



  

//XML FILE
applicationContext.xml

<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context
		http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!--    Exercise SPR1-->
    <bean id="myFirstBean" class="java.lang.String">
        <constructor-arg><value>Hello from Spring, 25848</value></constructor-arg>
    </bean>

//No longer needed to inject the QuestionsDAO individually, the package is injected through component-scan and @Service or @Repository
    <bean id="QuestionsDAO" class="fr.epita.quizDAOModel.QuestionsDAO">
    </bean>

    <bean id="mainDS" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="url" value="jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"></property>
        <property name="username" value="test"></property>
        <property name="password" value="test"></property>
        <property name="driverClassName" value="org.h2.Driver"></property>
    </bean>

//QuestionsDAO class is injected by the component-scan and @Service
    <context:component-scan base-package="fr.epita.quizDAOModel" />
      
</beans>
