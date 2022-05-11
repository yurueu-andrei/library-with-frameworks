package by.library.yurueu.service.impl;

import by.library.yurueu.dto.BookDamageDto;
import by.library.yurueu.dto.BookDamageListDto;
import by.library.yurueu.entity.BookDamage;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.mapper.BookDamageMapper;
import by.library.yurueu.repository.BookDamageRepository;
import by.library.yurueu.service.BookDamageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookDamageServiceImpl implements BookDamageService {
    private final BookDamageRepository bookDamageRepository;
    private final BookDamageMapper bookDamageMapper;

    @Override
    public BookDamageDto findById(Long id) throws ServiceException {
        return bookDamageRepository.findById(id).map(bookDamageMapper::toDTO)
                .orElseThrow(() -> new ServiceException(String.format("The bookDamage was not found. id = %d", id)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDamageListDto> findAll() throws ServiceException {
        try {
            return bookDamageMapper.toListDto(bookDamageRepository.findAll());
        } catch (Exception ex) {
            throw new ServiceException("The bookDamages were not found", ex);
        }
    }

    @Transactional
    @Override
    public BookDamageDto add(BookDamageDto bookDamageSaveDto) throws ServiceException {
        try {
            BookDamage bookDamage = bookDamageMapper.fromSaveDTO(bookDamageSaveDto);
            bookDamage.setStatus("ACTIVE");
            return bookDamageMapper.toSaveDTO(bookDamageRepository.save(bookDamage));
        } catch (Exception ex) {
            throw new ServiceException(String.format("The bookDamage was not saved. %s", bookDamageSaveDto), ex);
        }
    }

    @Transactional
    @Override
    public boolean delete(Long id) throws ServiceException {
        BookDamage bookDamage = bookDamageRepository.findById(id)
                .orElseThrow(() ->
                        new ServiceException(
                                String.format(
                                        "The bookDamage was not deleted. The bookDamage was not found. id = %d", id)));
        try {
            bookDamage.setStatus("DELETED");
            bookDamageRepository.flush();
            return true;
        } catch (Exception ex) {
            throw new ServiceException(String.format("The bookDamage was not deleted. id = %d", id), ex);
        }
    }
}