package by.library.yurueu.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.FetchType;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book_copies")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@AllArgsConstructor
public class BookCopy extends BaseEntity {
    @Column(name = "book_copy_status")
    private String status;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "price")
    private int price;

    @Column(name = "price_per_day")
    private int pricePerDay;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            mappedBy = "bookCopy",
            fetch = FetchType.LAZY
    )
    private Set<BookDamage> bookDamages;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "bookCopies")
    private Set<Order> orders = new HashSet<>();
}