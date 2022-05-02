package by.library.yurueu;

import by.library.yurueu.mapper.AuthorMapper;
import by.library.yurueu.repository.AuthorRepository;
import by.library.yurueu.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@EnableAspectJAutoProxy
@Profile("!test")
public class Runner implements CommandLineRunner {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private AuthorMapper authorMapper;

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        authorRepository.findAll();
        authorRepository.findById(3L);
    }
}