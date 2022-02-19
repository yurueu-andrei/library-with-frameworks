package by.library.yurueu.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "book_copies")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BookCopy extends BaseEntity {
    private Long id;
    private BookCopyStatus status;
    private LocalDate registrationDate;
    private int price;
    private int pricePerDay;
    private Long bookId;

    @Builder
    public BookCopy(Long id, BookCopyStatus status, LocalDate registrationDate, int price, int pricePerDay, Long bookId) {
        setId(id);
        this.status = status;
        this.registrationDate = registrationDate;
        this.price = price;
        this.pricePerDay = pricePerDay;
        this.bookId = bookId;
    }
}