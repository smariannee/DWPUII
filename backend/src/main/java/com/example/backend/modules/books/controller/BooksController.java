package com.example.backend.modules.books.controller;

import com.example.backend.kernel.ResponseApi;
import com.example.backend.modules.books.model.Book;
import com.example.backend.modules.books.model.BookDto;
import com.example.backend.modules.books.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = {"*"})
@AllArgsConstructor
public class BooksController {
    private final BookService bookService;

    @PostMapping("/")
    public ResponseEntity<ResponseApi<Book>> save (@Validated(BookDto.Save.class) @RequestBody BookDto dto){
        ResponseApi<Book> responseApi = bookService.save(dto);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }
    @DeleteMapping("/")
    public ResponseEntity<ResponseApi<?>> delete (@Validated(BookDto.Delete.class) @RequestBody BookDto dto){
        ResponseApi<?> responseApi = bookService.delete(dto);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }

    @PutMapping("/")
    public ResponseEntity<ResponseApi<Book>> update (@Validated(BookDto.Update.class) @RequestBody BookDto dto){
        ResponseApi<Book> responseApi = bookService.update(dto);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }

    @GetMapping("/paged")
    public ResponseEntity<ResponseApi<Page<Book>>> getAll (
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size,
            @RequestParam(defaultValue = "id", required = false) String sort,
            @RequestParam(defaultValue = "asc", required = false) String direction){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
        ResponseApi<Page<Book>> responseApi = bookService.getPaged(pageable);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }
    @GetMapping("/")
    public ResponseEntity<ResponseApi<Book>> getOne (@Validated(BookDto.GetOne.class) @RequestBody BookDto dto){
        ResponseApi<Book> responseApi = bookService.getOne(dto);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }
}
