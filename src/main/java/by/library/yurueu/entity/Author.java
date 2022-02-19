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
@Table(name = "authors")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Author extends BaseEntity {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String imagePath;

    @Builder
    public Author(Long id, String firstName, String lastName, LocalDate birthDate, String imagePath) {
        setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.imagePath = imagePath;
    }
}