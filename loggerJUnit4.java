package fr.epita.sandbox;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class TestLog4J {
    private static final Logger LOGGER = LogManager.getLogger(TestLog4J.class);

    @Test
    public void test(){
        //Threshold ERROR > WARNING > INFO > DEBUG > TRACE > ALL
        //Current Threshold             > the following can be used
        LOGGER.info("this is an info");
        LOGGER.info("returning");
    }
}

//Logger .xml file
<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{yyyy-MM-ddTHH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>
    </Appenders>
    <Loggers>
        <Root level="All">
            <AppenderRef ref="Console"/>
        </Root>
    </Loggers>
</Configuration>
