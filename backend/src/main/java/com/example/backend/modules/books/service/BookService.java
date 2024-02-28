package com.example.backend.modules.books.service;

import com.example.backend.kernel.ResponseApi;
import com.example.backend.modules.books.model.Book;
import com.example.backend.modules.books.model.BookDto;
import com.example.backend.modules.books.model.IBookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class BookService {
    private final IBookRepository iBookRepository;

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseApi<Book> save(BookDto dto){
        if(dto.getName().isBlank()) return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, "The name cannot be empty");
        if(dto.getAutor().isBlank()) return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, "The author cannot be empty");
        if(dto.getReleaseDate() == null) return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, "The release date cannot be empty");
        if(dto.getReleaseDate()> LocalDate.now().getYear()) return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, "The release date cannot be greater than the current date");

        Book book = new Book();
        book.save(dto);
        return new ResponseApi<>(iBookRepository.saveAndFlush(book),HttpStatus.CREATED, false, "Book saved successfully");
    }
    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseApi<?> delete (Long id){
        if(id == null || id <= 0) return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, "The id cannot be empty or less than 1");
        Optional<Book> book = iBookRepository.findById(id);
        if(book.isEmpty()) return new ResponseApi<>(HttpStatus.NOT_FOUND, true, "Book not found");
        iBookRepository.delete(book.get());
        return new ResponseApi<>(HttpStatus.OK, false, "Book deleted successfully");
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseApi<Book> update(BookDto dto){
        if(dto.getId() == null || dto.getId() <= 0) return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, "The id cannot be empty or less than 1");
        Optional<Book> book = iBookRepository.findById(dto.getId());
        if(book.isEmpty()) return new ResponseApi<>(HttpStatus.NOT_FOUND, true, "Book not found");
        if(dto.getName().isBlank()) return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, "The name cannot be empty");
        if(dto.getAutor().isBlank()) return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, "The author cannot be empty");
        if(dto.getReleaseDate() == null) return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, "The release date cannot be empty");
        if(dto.getReleaseDate()> LocalDate.now().getYear()) return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, "The release date cannot be greater than the current date");
        book.get().setName(dto.getName());
        book.get().setAutor(dto.getAutor());
        book.get().setReleaseDate(dto.getReleaseDate());
        book.get().setImg(dto.getImg());
        return new ResponseApi<>(iBookRepository.saveAndFlush(book.get()),HttpStatus.OK, false, "Book updated successfully");
    }

    @Transactional(readOnly = true)
    public ResponseApi<Book> getOne (BookDto dto){
        if(dto.getId() == null || dto.getId() <= 0) return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, "The id cannot be empty or less than 1");
        Optional<Book> book = iBookRepository.findById(dto.getId());
        return book.map(value -> new ResponseApi<>(value, HttpStatus.OK, false, "Book found successfully")).orElseGet(() -> new ResponseApi<>(HttpStatus.NOT_FOUND, true, "Book not found"));
    }

    @Transactional(readOnly = true)
    public ResponseApi<List<Book>> getAll(){
        return new ResponseApi<>(iBookRepository.getAll(), HttpStatus.OK, false, "Books found successfully");
    }
}
