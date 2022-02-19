package by.library.yurueu.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "book_damage")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BookDamage extends BaseEntity {
    private Long id;
    private String imagePath;
    private Long userId;
    private Long orderId;
    private Long bookCopyId;

    @Builder
    public BookDamage(Long id, String imagePath, Long userId, Long orderId, Long bookCopyId) {
        setId(id);
        this.imagePath = imagePath;
        this.userId = userId;
        this.orderId = orderId;
        this.bookCopyId = bookCopyId;
    }
}