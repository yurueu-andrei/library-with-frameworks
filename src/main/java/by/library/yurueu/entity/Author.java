package by.library.yurueu.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authors")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@AllArgsConstructor
public class Author extends BaseEntity {
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "image_path")
    private String imagePath;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(
            mappedBy = "authors",
            fetch = FetchType.LAZY
    )
    private Set<Book> books = new HashSet<>();
}