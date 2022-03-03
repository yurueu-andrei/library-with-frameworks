package by.library.yurueu.dto;

import java.util.Set;

public interface BookSaveDto {
    Long getId();
    String getTitle();
    int getPagesNumber();
    String getImagePath();
    Set<Long> getGenresId();
    Set<Long> getAuthorsId();

    void setId(Long id);
    void setTitle(String title);
    void setPagesNumber(int pagesNumber);
    void setImagePath(String imagePath);
    void setGenresId(Set<Long> genresId);
    void setAuthorsId(Set<Long> authorsId);
}