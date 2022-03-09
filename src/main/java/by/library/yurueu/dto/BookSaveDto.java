package by.library.yurueu.dto;

import java.util.List;

public interface BookSaveDto {
    Long getId();
    String getTitle();
    int getPagesNumber();
    String getImagePath();
    List<Long> getGenresId();
    List<Long> getAuthorsId();

    void setId(Long id);
    void setTitle(String title);
    void setPagesNumber(int pagesNumber);
    void setImagePath(String imagePath);
    void setGenresId(List<Long> genresId);
    void setAuthorsId(List<Long> authorsId);
}