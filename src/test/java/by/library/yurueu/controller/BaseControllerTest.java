package by.library.yurueu.controller;

import by.library.yurueu.repository.AuthorRepository;
import by.library.yurueu.repository.BookCopyRepository;
import by.library.yurueu.repository.BookDamageRepository;
import by.library.yurueu.repository.BookRepository;
import by.library.yurueu.repository.GenreRepository;
import by.library.yurueu.repository.OrderRepository;
import by.library.yurueu.repository.RoleRepository;
import by.library.yurueu.repository.UserRepository;
import by.library.yurueu.service.AuthorService;
import by.library.yurueu.service.BookCopyService;
import by.library.yurueu.service.BookDamageService;
import by.library.yurueu.service.BookService;
import by.library.yurueu.service.GenreService;
import by.library.yurueu.service.OrderService;
import by.library.yurueu.service.RoleService;
import by.library.yurueu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@MockBean(classes = {
        UserRepository.class,
        BookRepository.class,
        AuthorRepository.class,
        BookCopyRepository.class,
        BookDamageRepository.class,
        RoleRepository.class,
        OrderRepository.class,
        GenreRepository.class
} )
@AutoConfigureMockMvc
public class BaseControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected AuthorService authorService;

    @MockBean
    protected BookCopyService bookCopyService;

    @MockBean
    protected BookService bookService;

    @MockBean
    protected BookDamageService bookDamageService;

    @MockBean
    protected GenreService genreService;

    @MockBean
    protected OrderService orderService;

    @MockBean
    protected RoleService roleService;

    @MockBean
    protected UserService userService;
}