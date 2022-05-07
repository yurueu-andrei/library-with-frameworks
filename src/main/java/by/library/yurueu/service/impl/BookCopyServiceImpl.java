package by.library.yurueu.service.impl;

import by.library.yurueu.dto.BookCopyDto;
import by.library.yurueu.dto.BookCopyListDto;
import by.library.yurueu.dto.BookCopySaveAndUpdateDto;
import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.mapper.BookCopyMapper;
import by.library.yurueu.repository.BookCopyRepository;
import by.library.yurueu.repository.BookDamageRepository;
import by.library.yurueu.service.BookCopyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookCopyServiceImpl implements BookCopyService {
    private final BookCopyRepository bookCopyRepository;
    private final BookDamageRepository bookDamageRepository;
    private final BookCopyMapper bookCopyMapper;

    @Override
    public BookCopyDto findById(Long id) throws ServiceException {
        return bookCopyRepository.findById(id).map(bookCopyMapper::toDTO)
                .orElseThrow(() -> new ServiceException(String.format("The bookCopy was not found. id = %d", id)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookCopyListDto> findAll() throws ServiceException {
        try {
            return bookCopyMapper.toListDto(bookCopyRepository.findAll());
        } catch (Exception ex) {
            throw new ServiceException("The bookCopies were not found", ex);
        }
    }

    @Transactional
    @Override
    public BookCopySaveAndUpdateDto add(BookCopySaveAndUpdateDto bookCopySaveAndUpdateDto) throws ServiceException {
        try {
            BookCopy bookCopy = bookCopyMapper.fromSaveOrUpdateDTO(bookCopySaveAndUpdateDto);
            bookCopy.setStatus("AVAILABLE");
            return bookCopyMapper.toSaveDTO(bookCopyRepository.save(bookCopy));
        } catch (Exception ex) {
            throw new ServiceException(String.format("The bookCopy was not saved. %s", bookCopySaveAndUpdateDto), ex);
        }
    }

    @Transactional
    @Override
    public boolean update(BookCopySaveAndUpdateDto bookCopyUpdateDto) throws ServiceException {
        BookCopy bookCopy = bookCopyRepository.findById(bookCopyUpdateDto.getId())
                .orElseThrow(()->
                        new ServiceException(
                                String.format(
                                        "The bookCopy was not updated. The bookCopy was not found. id = %d",
                                        bookCopyUpdateDto.getId())));
        try {
            settingUpdatedFields(bookCopy, bookCopyUpdateDto);
            bookCopyRepository.flush();
            return true;
        } catch (Exception ex) {
            throw new ServiceException(String.format("The author was not updated. %s", bookCopyUpdateDto), ex);
        }
    }

    private void settingUpdatedFields(BookCopy bookCopy, BookCopySaveAndUpdateDto bookCopySaveAndUpdateDto) {
        if (bookCopySaveAndUpdateDto.getStatus() != null) {
            bookCopy.setStatus(bookCopySaveAndUpdateDto.getStatus());
        }
        if (bookCopySaveAndUpdateDto.getRegistrationDate() != null) {
            bookCopy.setRegistrationDate(bookCopySaveAndUpdateDto.getRegistrationDate());
        }
        if (bookCopySaveAndUpdateDto.getPricePerDay() != 0) {
            bookCopy.setPricePerDay(bookCopySaveAndUpdateDto.getPricePerDay());
        }
        if (bookCopySaveAndUpdateDto.getImagePath() != null) {
            bookCopy.setImagePath(bookCopySaveAndUpdateDto.getImagePath());
        }
        if (bookCopySaveAndUpdateDto.getBookId() != null) {
            bookCopy.setBook(Book.builder().id(bookCopySaveAndUpdateDto.getBookId()).build());
        }
    }

    @Transactional
    @Override
    public boolean delete(Long id) throws ServiceException {
        BookCopy bookCopy = bookCopyRepository.findById(id)
                .orElseThrow(() ->
                        new ServiceException(
                                String.format(
                                        "The bookCopy was not deleted. The bookCopy was not found. id = %d", id)));
        try {
            deleteLinks(bookCopy);
            bookCopy.setStatus("DELETED");
            bookCopyRepository.flush();
            return true;
        } catch (Exception ex) {
            throw new ServiceException(String.format("The bookCopy was not deleted. id = %d", id), ex);
        }
    }

    private void deleteLinks(BookCopy bookCopy) {
        bookCopy.getBookDamages().forEach(bookDamage -> {
            bookDamage.setStatus("DELETED");
            bookDamageRepository.save(bookDamage);
        });
    }
}