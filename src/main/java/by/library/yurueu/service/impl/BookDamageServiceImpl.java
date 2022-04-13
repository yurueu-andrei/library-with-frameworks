package by.library.yurueu.service.impl;

import by.library.yurueu.converter.BookDamageConverter;
import by.library.yurueu.dto.BookDamageDto;
import by.library.yurueu.dto.BookDamageListDto;
import by.library.yurueu.dto.BookDamageSaveDto;
import by.library.yurueu.entity.BookDamage;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.BookDamageRepository;
import by.library.yurueu.service.BookDamageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookDamageServiceImpl implements BookDamageService {
    private final BookDamageRepository bookDamageRepository;

    @Override
    public BookDamageDto findById(Long id) throws ServiceException {
        return bookDamageRepository.findById(id).map(BookDamageConverter::toDTO)
                .orElseThrow(() -> new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not found")));
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDamageListDto> findAll() throws ServiceException {
        try {
            return BookDamageConverter.toListDTO(bookDamageRepository.findAll());
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName() + "s", "were not found"));
        }
    }

    @Transactional
    @Override
    public BookDamageSaveDto add(BookDamageSaveDto bookDamageSaveDto) throws ServiceException {
        try {
            BookDamage bookDamage = BookDamageConverter.fromSaveDTO(bookDamageSaveDto);
            return BookDamageConverter.toSaveDTO(bookDamageRepository.save(bookDamage));
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not added"));
        }
    }

    @Transactional
    @Override
    public boolean delete(Long id) throws ServiceException {
        Optional<BookDamage> bookDamageToDelete = bookDamageRepository.findById(id);
        if (bookDamageToDelete.isPresent()) {
            BookDamage bookDamage = bookDamageToDelete.get();
            bookDamage.setStatus("DELETED");
            bookDamageRepository.save(bookDamage);
            return true;
        }
        throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not deleted"));
    }
}