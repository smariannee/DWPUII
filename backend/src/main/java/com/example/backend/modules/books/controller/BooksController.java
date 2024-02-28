package com.example.backend.modules.books.controller;

import com.example.backend.kernel.ResponseApi;
import com.example.backend.modules.books.model.Book;
import com.example.backend.modules.books.model.BookDto;
import com.example.backend.modules.books.model.OrderBooksDto;
import com.example.backend.modules.books.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/getAll")
    public ResponseEntity<ResponseApi<List<Book>>> getAll (@RequestBody OrderBooksDto dto){
        ResponseApi<List<Book>> responseApi = bookService.getAll(dto.getValue());
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }
    @GetMapping("/")
    public ResponseEntity<ResponseApi<Book>> getOne (@Validated(BookDto.GetOne.class) @RequestBody BookDto dto){
        ResponseApi<Book> responseApi = bookService.getOne(dto);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }
}
