package fr.epita.maths;

//CLASS IterativeFactirial
public class IterativeFactorial implements IFactorial {
    public int calculateFactorial(int n) {
        int result = n;
        for (int i = 0; i < n; i++) {
            result *= 1;
        }
        return result;
    }
}



package fr.epita.maths;

//CLASS RecursiveFactorial
public class RecursiveFactorial implements IFactorial {
    public int calculateFactorial(int n) {
        if (n <= 1){
            return 1;
        } else {
            return n * calculateFactorial(n - 1);
        }
    }
}




package fr.epita.maths;

//INTERFACE
public interface IFactorial {
    int calculateFactorial(int n);
}



//MAIN
package fr.epita.quiz.tests;

import fr.epita.maths.IFactorial;
import fr.epita.maths.IterativeFactorial;
import org.junit.Assert;
import org.junit.Test;

public class TestJUnit {

    @Test
    public void myFirstTest(){
        //given
        int number = 5;

        //when
        IFactorial factorial = new IterativeFactorial();
        int iterativeResult = factorial.calculateFactorial(number);
        int recursiveResult = factorial.calculateFactorial(number);

        //then
        Assert.assertEquals(120, iterativeResult);
        Assert.assertEquals(120, recursiveResult);
    }
}



