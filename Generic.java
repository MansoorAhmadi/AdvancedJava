


//GENERIC JAPDAO
package fr.epita.quiz.services.data.impl;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import fr.epita.quiz.services.data.api.IDAO;

public  class GenericJPADAO<T> implements IDAO<T>{

    @Inject
    SessionFactory factory;

    @Override
    public void create(T object){

        Session session = factory.openSession();
        session.save(object);
        session.close();
    }

    @Override
    public void update(T obj){
        Session session = factory.openSession();
        session.update(obj);
        session.close();
    }

    @Override
    public void delete(T obj){
        Session session = factory.openSession();
        session.delete(obj);
        session.close();
    }

    @Override
    public List<T> search(T obj) {
        return null;
    }


}






//QUESTIONJPADAO

package fr.epita.quiz.services.data.impl;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.services.data.api.IQuestionDAO;


@Repository
public class QuestionJPADAO extends GenericJPADAO<Question> implements IQuestionDAO {

    @Inject
    SessionFactory factory;

    public List<Question> search(Question question){
        Session session = factory.openSession();
        Query<Question> query = session.createQuery("from Question where questionTitle like 'What is%'", Question.class);
        List<Question> resultList = query.list();

        session.close();

        return resultList;
    }


}





//IDAO
package fr.epita.quiz.services.data.api;

import java.util.List;

public interface IDAO<T> {
    void create(T object);

    void update(T obj);

    void delete(T obj);

    List<T> search (T obj);
}




