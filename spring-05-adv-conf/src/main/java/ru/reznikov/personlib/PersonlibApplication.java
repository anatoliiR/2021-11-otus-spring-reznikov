package ru.reznikov.personlib;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.sql.SQLException;

@SpringBootApplication
public class PersonlibApplication {

    public static void main(String[] args) throws SQLException {
        ApplicationContext context = SpringApplication.run(PersonlibApplication.class, args);
        Console.main(args);

    }

}
