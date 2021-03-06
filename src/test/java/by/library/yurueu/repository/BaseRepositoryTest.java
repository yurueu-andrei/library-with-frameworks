package by.library.yurueu.repository;

import by.library.yurueu.entity.Author;
import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.entity.BookDamage;
import by.library.yurueu.entity.Genre;
import by.library.yurueu.entity.Order;
import by.library.yurueu.entity.Role;
import by.library.yurueu.entity.User;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public abstract class BaseRepositoryTest {
    @Autowired
    private Flyway flyway;
    private List<User> users;
    private List<Role> roles;
    private List<Order> orders;
    private List<Genre> genres;
    private List<Book> books;
    private List<BookDamage> bookDamage;
    private List<BookCopy> bookCopies;
    private List<Author> authors;

    public BaseRepositoryTest() {
        fillUsers();
        fillRoles();
        fillOrders();
        fillGenres();
        fillBooks();
        fillBookDamage();
        fillBookCopies();
        fillAuthors();
    }

    @BeforeEach
    public void migrate() {
        flyway.migrate();
    }

    @AfterEach
    public void clean() {
        flyway.clean();
    }

    private void fillUsers() {
        users = new ArrayList<>() {{
            add(User.builder().id(1L).firstName("vlad").lastName("kaliaha").passportNumber("1111").email("email1").password("$2a$12$Uagtw7S34f0.YNwPIJgIOu8OE5JGY9Oor1E9vX2Bf5T3j24P5qLP2").address("address1").birthDate(LocalDate.of(2005, 6, 6)).status("ACTIVE").build());
            add(User.builder().id(2L).firstName("andrei").lastName("yurueu").passportNumber("2222").email("email2").password("$2a$12$31P2DhEMk1Wfs.9aIQn92unceFrbn.d2DFkX583UI/V9AATctMztC").address("address2").birthDate(LocalDate.of(2001, 6, 6)).status("ACTIVE").build());
            add(User.builder().id(3L).firstName("yaroslav").lastName("vasilevski").passportNumber("3333").email("email3").password("345").address("address3").birthDate(LocalDate.of(1998, 6, 6)).status("ACTIVE").build());
            add(User.builder().id(4L).firstName("anastasiya").lastName("yurkova").passportNumber("4444").email("email4").password("567").address("address4").birthDate(LocalDate.of(1999, 6, 6)).status("ACTIVE").build());
            add(User.builder().id(5L).firstName("alexander").lastName("kupriyanenko").passportNumber("5555").email("email5").password("789").address("address5").birthDate(LocalDate.of(1996, 6, 6)).status("ACTIVE").build());
        }};
    }

    public User findUserForFindById() {
        return users.get(0);
    }

    public List<User> findUsersForFindAll() {
        return users;
    }

    private void fillRoles() {
        roles = new ArrayList<>() {{
            add(Role.builder().id(1L).roleName("admin").build());
            add(Role.builder().id(2L).roleName("user").build());
        }};
    }

    public Role findRoleForFindById() {
        return roles.get(0);
    }

    public List<Role> findRolesForFindAll() {
        return roles;
    }

    private void fillOrders() {
        orders = new ArrayList<>() {{
            add(Order.builder().id(1L).status("NEW").startDate(LocalDate.of(1998, 6, 6)).endDate(LocalDate.of(1998, 6, 6)).price(243).user(User.builder().id(1L).build()).build());
            add(Order.builder().id(2L).status("NEW").startDate(LocalDate.of(1998, 6, 6)).endDate(LocalDate.of(1998, 6, 6)).price(21).user(User.builder().id(1L).build()).build());
            add(Order.builder().id(3L).status("NEW").startDate(LocalDate.of(1998, 6, 6)).endDate(LocalDate.of(1998, 6, 6)).price(253).user(User.builder().id(1L).build()).build());
            add(Order.builder().id(4L).status("NEW").startDate(LocalDate.of(1998, 6, 6)).endDate(LocalDate.of(1998, 6, 6)).price(273).user(User.builder().id(3L).build()).build());
            add(Order.builder().id(5L).status("NEW").startDate(LocalDate.of(1998, 6, 6)).endDate(LocalDate.of(1998, 6, 6)).price(238).user(User.builder().id(4L).build()).build());
        }};
    }

    public Order findOrderForFindById() {
        return orders.get(0);
    }

    public List<Order> findOrdersForFindAll() {
        return orders;
    }

    private void fillGenres() {
        genres = new ArrayList<>() {{
            add(Genre.builder().id(1L).genreName("NOVEL").build());
            add(Genre.builder().id(2L).genreName("ADVENTURE").build());
            add(Genre.builder().id(3L).genreName("COMEDY").build());
            add(Genre.builder().id(4L).genreName("CRIME").build());
            add(Genre.builder().id(5L).genreName("HORROR").build());
            add(Genre.builder().id(6L).genreName("SCIENCE FICTION").build());
            add(Genre.builder().id(7L).genreName("ROMANCE").build());
        }};
    }

    public Genre findGenreForFindById() {
        return genres.get(0);
    }

    public List<Genre> findGenresForFindAll() {
        return genres;
    }

    private void fillBooks() {
        books = new ArrayList<>() {{
            add(Book.builder().id(1L).title("War and peace").pagesNumber(1365).imagePath("image path").status("ACTIVE").build());
            add(Book.builder().id(2L).title("The Master and Margarita").pagesNumber(638).imagePath("image path").status("ACTIVE").build());
            add(Book.builder().id(3L).title("Idiot").pagesNumber(496).imagePath("image path").status("ACTIVE").build());
            add(Book.builder().id(4L).title("The old man and the sea").pagesNumber(153).imagePath("image path").status("ACTIVE").build());
            add(Book.builder().id(5L).title("Eugene Onegin").pagesNumber(462).imagePath("image path").status("ACTIVE").build());
        }};
    }

    public Book findBookForFindById() {
        return books.get(0);
    }

    public List<Book> findBooksForFindAll() {
        return books;
    }

    private void fillBookDamage() {
        bookDamage = new ArrayList<>() {{
            add(BookDamage.builder().id(1L).imagePath("image path").damageDescription("damage1").status("ACTIVE").build());
            add(BookDamage.builder().id(2L).imagePath("image path").damageDescription("damage2").status("ACTIVE").build());
            add(BookDamage.builder().id(3L).imagePath("image path").damageDescription("damage3").status("ACTIVE").build());
            add(BookDamage.builder().id(4L).imagePath("image path").damageDescription("damage4").status("ACTIVE").build());
        }};
    }

    public BookDamage findBookDamageForFindById() {
        return bookDamage.get(0);
    }

    public List<BookDamage> findBookDamageForFindAll() {
        return bookDamage;
    }

    private void fillBookCopies() {
        bookCopies = new ArrayList<>() {{
            add(BookCopy.builder().id(1L).status("AVAILABLE").registrationDate(LocalDate.of(2019, 3, 1)).imagePath("image path").pricePerDay(150).build());
            add(BookCopy.builder().id(2L).status("AVAILABLE").registrationDate(LocalDate.of(2020, 6, 1)).imagePath("image path").pricePerDay(210).build());
            add(BookCopy.builder().id(3L).status("AVAILABLE").registrationDate(LocalDate.of(2021, 8, 4)).imagePath("image path").pricePerDay(225).build());
            add(BookCopy.builder().id(4L).status("AVAILABLE").registrationDate(LocalDate.of(2017, 10, 10)).imagePath("image path").pricePerDay(128).build());
            add(BookCopy.builder().id(5L).status("AVAILABLE").registrationDate(LocalDate.of(2020, 6, 2)).imagePath("image path").pricePerDay(311).build());
        }};
    }

    public BookCopy findBookCopyForFindById() {
        return bookCopies.get(0);
    }

    public List<BookCopy> findBookCopiesForFindAll() {
        return bookCopies;
    }

    private void fillAuthors() {
        authors = new ArrayList<>() {{
            add(Author.builder().id(1L).firstName("Lev").lastName("Tolstoy").birthDate(LocalDate.of(1879, 4, 4)).imagePath("image path").status("ACTIVE").build());
            add(Author.builder().id(2L).firstName("Ernest").lastName("Hemingway").birthDate(LocalDate.of(1903, 7, 7)).imagePath("image path").status("ACTIVE").build());
            add(Author.builder().id(3L).firstName("Mikhail").lastName("Bulgakov").birthDate(LocalDate.of(1885, 10, 10)).imagePath("image path").status("ACTIVE").build());
            add(Author.builder().id(4L).firstName("Alexander").lastName("Pushkin").birthDate(LocalDate.of(1852, 2, 2)).imagePath("image path").status("ACTIVE").build());
            add(Author.builder().id(5L).firstName("Fedor").lastName("Dostoevsky").birthDate(LocalDate.of(1845, 1, 1)).imagePath("image path").status("ACTIVE").build());
        }};
    }

    public Author findAuthorForFindById() {
        return authors.get(0);
    }

    public List<Author> findAuthorsForFindAll() {
        return authors;
    }

}