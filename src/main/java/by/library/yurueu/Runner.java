package by.library.yurueu;

import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.service.FlywayService;

import static by.library.yurueu.service.Property.*;

public class Runner {
    public static void main(String[] args) throws RepositoryException, ServiceException {
        FlywayService flywayService = new FlywayService(H2_URL, H2_USER, H2_PASSWORD, MIGRATION_LOCATION);
        flywayService.migrate();


    }
}