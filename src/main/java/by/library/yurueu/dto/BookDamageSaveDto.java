package by.library.yurueu.dto;

public interface BookDamageSaveDto {
    Long getId();
    String getImagePath();
    String getDamageDescription();
    Long getUserId();
    Long getOrderId();
    Long getBookCopyId();

    void setId(Long id);
    void setImagePath(String imagePath);
    void setDamageDescription(String damageDescription);
    void setUserId(Long userId);
    void setOrderId(Long orderId);
    void setBookCopyId(Long bookCopyId);
}