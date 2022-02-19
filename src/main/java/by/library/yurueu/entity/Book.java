package by.library.yurueu.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Book extends BaseEntity {
    private Long id;
    private String title;
    private int pagesNumber;
    private String imagePath;

    @Builder
    public Book(Long id, String title, int pagesNumber, String imagePath) {
        setId(id);
        this.title = title;
        this.pagesNumber = pagesNumber;
        this.imagePath = imagePath;
    }
}